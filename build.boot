(set-env!
 :source-paths   #{"src"}
 :dependencies '[[org.clojure/clojure "1.8.0" :scope "provided"]
                 [boot/core "2.5.5" :scope "provided"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[mbuczko.boot-build-info :refer [build-info]])

(def +version+ "0.1.0")

(bootlaces! +version+)

(task-options!
 pom {:project 'mbuczko/boot-build-info
      :version +version+
      :description "Generates build info based of all the intersting stuff returned by git"
      :url "https://github.com/mbuczko/boot-build-info"
      :scm {:url "https://github.com/mbuczko/boot-build-info"}
      :license {"Eclipse Public License" "http://www.eclipse.org/legal/epl-v10.html"}})
