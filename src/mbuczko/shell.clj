(ns mbuczko.shell
  (:require [cheshire.core :as json]
            [clj-time.core :as t]
            [clj-time.format :as tf]
            [clojure.java.io :as io]
            [clojure.java.shell :as shell]
            [clojure.string :as str]))


(def exec-str
  (comp (partial apply shell/sh) #(str/split % #" ")))

(defmacro list+
  [& forms]
  (->> forms
       (map (fn [x] (if (and (symbol? x)
                             (not (contains? &env x))
                             (not (resolve x)))
                      (name x)
                      x)))
       (cons `list)))

(defmacro sh
  [& args]
  `(some->> (list+ ~@args)
            (str/join " ")
            exec-str
            :out))

(defmacro git
  [& args]
  `(-> (sh "git" ~@args)
       str/trimr))

(defn now-iso-str
  []
  (tf/unparse (tf/formatters :date-time) (t/now)))

(defn build-info [kv]
  (merge
   {:sha (git rev-parse --short HEAD)
    :tag (git describe --abbrev=0 --tags HEAD)
    :branch (git rev-parse --abbrev-ref HEAD)
    :timestamp (now-iso-str)} kv))

(defmacro write-build-info
  [path kv]
  `(with-open [out# (io/writer ~path)]
     (json/generate-stream (build-info ~kv) out#)))
