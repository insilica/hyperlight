(ns build
  (:refer-clojure :exclude [test])
  (:require [org.corfield.build :as bb]))

(def lib 'co.insilica/hyperlight)
(def version "1.0.0")
(defn get-version [opts]
  (str version (when (:snapshot opts) "-SNAPSHOT")))

(defn test [opts]
  (-> opts
      (assoc :main-args ["-m" "kaocha.runner"])
      (bb/run-task [:test]))
  opts)

(defn ci "Run the CI pipeline of tests (and build the JAR)." [opts]
  (-> opts
      (assoc :lib lib :version (get-version opts))
      bb/clean
      test
      bb/clean
      (assoc :src-pom "template/pom.xml")
      bb/jar))

(defn deploy "Deploy the JAR to Clojars." [opts]
  (-> opts
      (assoc :lib lib :version (get-version opts))
      bb/deploy))
