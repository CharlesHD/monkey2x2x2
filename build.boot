(set-env!
 :source-paths #{"src"}
 :resource-paths #{"res"}
 :target-path "tmp"
 :dependencies '[[org.clojure/clojure "1.9.0-alpha14"]
                 [seesaw "1.4.5"]])

(task-options!
 pom {:project 'monkey2x2x2
      :version "0.0.2"}
 aot {:namespace '#{monkey2x2x2.core}}
 jar {:manifest {"Foo" "bar"}
      :main 'monkey2x2x2.core})

(deftask build
  "Builds an uberjar of this project that can be run with java -jar"
  []
  (comp
   (aot)
   (pom)
   (uber)
   (jar)
   (sift :include #{#"project.jar"})
   (target)))
