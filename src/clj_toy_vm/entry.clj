(ns clj-toy-vm.entry
  (:require [clojure.string :as string]))

(def Path-List-Separator ":")

(defn new-entry
  "Load entries by path"
  [path]
  (cond
    (string/includes? path Path-List-Separator)
    ; new composite entry
    (string/ends-with? path "*")
    ; new wildcard entry
    (or (string/ends-with? path ".jar")
        (string/ends-with? path ".JAR")
        (string/ends-with? path ".zip")
        (string/ends-with? path ".ZIP"))
    ; new zip entry
    :else
    ; new dir entry
    ))