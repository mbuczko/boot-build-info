(ns mbuczko.boot-build-info
  {:boot/export-tasks true}
  (:require
   [clojure.java.io :as io]
   [boot.pod        :as pod]
   [boot.core       :as core]
   [boot.util       :as util]))

(def ^:private build-info-deps '[[cheshire "5.6.1"]
                                 [clj-time "0.11.0"]])

(def ^:private default-location "resources/build.json")

(core/deftask build-info
  "Generates build information composed of git sha/branch and user's defined data"
  [d dest  DESTINATION str   "location where build info json will be created"
   b build BUILD=VAL{kw str} "additional build data"]

  (let [worker (pod/make-pod (update-in (core/get-env) [:dependencies] into build-info-deps))
        config (merge {:dest default-location} *opts*)]

    (core/with-pre-wrap [fs]
      (pod/with-eval-in worker
        (require '[mbuczko.shell :as shell]
                 '[boot.util     :as util])

        (let [destination (:dest ~config)
              build-data  (:build ~config)]
          (util/info (str "Generating build info into: " destination))
          (shell/write-build-info destination build-data)))
      fs)))
