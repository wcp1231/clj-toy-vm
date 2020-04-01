(ns clj-toy-vm.core
  (:require [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]])
  (:gen-class))

(def cli-options
  ;; An option with a required argument
  [[nil "--help" "Print usage" :id :help]
   [nil "--version" "Print version" :id :version]
   [nil "--classpath CLASS_PATH" "User classpath"]
   [nil "--Xjre JRE_OPTION" "Path to jre"]])


(defn usage [options-summary]
  (->> ["Usage: %s [-options] main-class [args...]"
        ""
        "Options:"
        options-summary]
       (string/join \newline)))


(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))


(defn validate-args
  "一个简单的参数校验和解析
  返回 main-class、options 和 arguments"
  [args]
  (let [{:keys [options arguments errors summary]} (clojure.tools.cli/parse-opts args cli-options)]
    (cond
      (:help options)
      {:exit-message (usage summary) :ok? true}
      (:version options)
      {:exit-message (str "Version 0.0.1") :ok? true}
      errors
      {:exit-message (error-msg errors)}
      (< 1 (count arguments))
      {:main-class (first arguments) :options options :arguments (rest arguments)}
      :else
      {:exit-message (usage summary)})))


(defn exit [status msg]
  (println msg)
  (System/exit status))


(defn -main [& args]
  (let [{:keys [main-class options arguments exit-message ok?]} (validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (println (str "main: " main-class " options" options " arguments" arguments)))))
