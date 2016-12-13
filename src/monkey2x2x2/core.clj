(ns monkey2x2x2.core
  (:gen-class)
  (:require [seesaw.core :as gui]))
;;         ___bleu__
;;         | a1 a2 |
;;         | a3 a4 |
;; __Rouge___Jaune___orange_
;; | b1 b2 | c1 c2 | d1 d2 |
;; | b3 b4 | c3 c4 | d3 d4 |
;; ___________vert__________
;;         | e1 e2 |
;;         | e3 e4 |
;;         __blanc__
;;         | f1 f2 |
;;         | f3 f4 |
;;         _________

(def cube
  {:a1 :blue :a2 :blue :a3 :blue :a4 :blue
   :b1 :red :b2 :red :b3 :red :b4 :red
   :c1 :yellow :c2 :yellow :c3 :yellow :c4 :yellow
   :d1 :orange :d2 :orange :d3 :orange :d4 :orange
   :e1 :green :e2 :green :e3 :green :e4 :green
   :f1 :white :f2 :white :f3 :white :f4 :white
   })

(defn print-cube
  [cube]
  (let
      [prnx
       (fn [n a1 a2 a3 a4]
         (println "Face" n ":" (a1 cube) (a2 cube))
         (println "Face" n ":" (a3 cube) (a4 cube)))]
    (prnx "a" :a1 :a2 :a3 :a4)
    (prnx "b" :b1 :b2 :b3 :b4)
    (prnx "c" :c1 :c2 :c3 :c4)
    (prnx "d" :d1 :d2 :d3 :d4)
    (prnx "e" :e1 :e2 :e3 :e4)
    (prnx "f" :f1 :f2 :f3 :f4)))

(defn print-cube2
  [c]
  (let [f (fn [k & rest]
            (apply
             str
             (case (get c k)
              :blue "B"
              :red "R"
              :yellow "Y"
              :orange "O"
              :green "G"
              :white "W") rest))]
    (println "    |" (f :a1) (f :a2) "|")
    (println "    |" (f :a3) (f :a4) "|")
    (println (f :b1) (f :b2) "|" (f :c1) (f :c2) "|" (f :d1) (f :d2))
    (println (f :b3) (f :b4) "|" (f :c3) (f :c4) "|" (f :d3) (f :d4))
    (println "    |" (f :e1) (f :e2) "|")
    (println "    |" (f :e3) (f :e4) "|")
    (println "    |" (f :f1) (f :f2) "|")
    (println "    |" (f :f3) (f :f4) "|")
    ))

(defn eval-mapping
  [mapping cube]
  (reduce-kv
   (fn [c k v]
     (assoc c k (get cube v)))
   cube mapping))

(def cube-identity
  {:a1 :a1 :a2 :a2 :a3 :a3 :a4 :a4
   :b1 :b1 :b2 :b2 :b3 :b3 :b4 :b4
   :c1 :c1 :c2 :c2 :c3 :c3 :c4 :c4
   :d1 :d1 :d2 :d2 :d3 :d3 :d4 :d4
   :e1 :e1 :e2 :e2 :e3 :e3 :e4 :e4
   :f1 :f1 :f2 :f2 :f3 :f3 :f4 :f4
   })
;;         ___L'____
;;         | c1 a2 |
;;         | c3 a4 |
;; _________________________
;; | b2 b4 | e1 c2 | d1 d2 |
;; | b1 b3 | e3 c4 | d3 d4 |
;; _________________________
;;         | f1 e2 |
;;         | f3 e4 |
;;         _________
;;         | a1 f2 |
;;         | a3 f4 |
;;         _________
(def L'
  {:a1 :c1 :a2 :a2 :a3 :c3 :a4 :a4
   :b1 :b2 :b2 :b4 :b3 :b1 :b4 :b3
   :c1 :e1 :c2 :c2 :c3 :e3 :c4 :c4
   :d1 :d1 :d2 :d2 :d3 :d3 :d4 :d4
   :e1 :f1 :e2 :e2 :e3 :f3 :e4 :e4
   :f1 :a1 :f2 :f2 :f3 :a3 :f4 :f4})

(def L2
  (eval-mapping L' L'))
(def L
  (eval-mapping L' L2))

;;         ___bleu__
;;         | a2 a4 |
;;         | a1 a3 |
;; __Rouge___Jaune___orange_
;; | f4 f3 | b1 b2 | c1 c2 |
;; | b3 b4 | c3 c4 | d3 d4 |
;; ___________vert__________
;;         | e1 e2 |
;;         | e3 e4 |
;;         __blanc__
;;         | f1 f2 |
;;         | d2 d1 |
;;         _________

(def U'
  (assoc cube-identity
         :a1 :a2 :a2 :a4
         :a3 :a1 :a4 :a3
         :b1 :f4 :b2 :f3
         :c1 :b1 :c2 :b2
         :d1 :c1 :d2 :c2
         :f3 :d2 :f4 :d1))
(def U2
  (eval-mapping U' U'))
(def U
  (eval-mapping U' U2))

;;         ___bleu__
;;         | a1 a2 |
;;         | d1 d3 |
;; __Rouge___Jaune___orange_
;; | b1 a4 | c2 c4 | e2 d2 |
;; | b3 a3 | c1 c3 | e1 d4 |
;; ___________vert__________
;;         | b2 b4 |
;;         | e3 e4 |
;;         __blanc__
;;         | f1 f2 |
;;         | f3 f4 |
;;         _________
(def F'
  (assoc cube-identity
         :c1 :c2 :c2 :c4
         :c3 :c1 :c4 :c3
         :a3 :d1 :a4 :d3
         :b2 :a4 :b4 :a3
         :e1 :b2 :e2 :b4
         :d1 :e2 :d3 :e1))
(def F2
  (eval-mapping F' F'))
(def F
  (eval-mapping F2 F'))

(defn win?
  [cube]
  (let
      [same-face
       (fn [a b c d]
         (= (a cube) (b cube) (c cube) (d cube)))]
    (and
     (same-face :a1 :a2 :a3 :a4)
     (same-face :b1 :b2 :b3 :b4)
     (same-face :c1 :c2 :c3 :c4)
     (same-face :d1 :d2 :d3 :d4)
     (same-face :e1 :e2 :e3 :e4)
     (same-face :f1 :f2 :f3 :f4))))

(def operations
  [L L' L2 U U' U2 F F' F2])

(def allowed-moves
  (let [lop [L L' L2]
        uop [U U' U2]
        fop [F F' F2]]
    (as-> {} n
        (reduce #(assoc %1 %2 (concat uop fop)) n lop)
        (reduce #(assoc %1 %2 (concat lop fop)) n uop)
        (reduce #(assoc %1 %2 (concat uop lop)) n fop)
        )))

(defn random-op
  ([prec-op]
   (rand-nth (get allowed-moves prec-op)))
  ([]
   (rand-nth operations)))

(defn random-cube
  [min max]
  (loop [c cube
         prec-ops '()
         n (+ min (rand-int (- max min)))]
    (if (= 0 n) {:cube c :moves (reverse prec-ops)}
     (let [opsym (if (empty? prec-ops)
               (random-op)
               (random-op (first prec-ops)))
           op opsym]
      (recur (eval-mapping op c) (cons opsym prec-ops) (dec n))))))

(defn monkey-solve
  [cube]
  (loop [c cube
         prec-ops '()]
    (if (win? c) (count prec-ops)
        (let [opsym (if (empty? prec-ops)
                      (random-op)
                      (random-op (first prec-ops)))
              op opsym]
          (recur (eval-mapping op c) (cons opsym prec-ops))))))

(defn results
  [ncubes]
  (let [c (->> (repeat ncubes 100)
               (map (partial random-cube 11))
               (map :cube)
               (pmap monkey-solve))
        mmax (apply max c)
        mmin (apply min c)
        average (float (/ (reduce + c) (count c)))]
    {:min mmin :max mmax :avg average}))

(defn -main
  []
  (gui/native!)
  (let [f (gui/frame :title "monkey2x2x2" :on-close :exit)
        lb (gui/listbox :model (range 1 30))
        b (gui/button :text "Vas-y le singe !")]
    (gui/config! f :content
                 (gui/left-right-split
                  lb b :divider-location 1/3))
    (gui/listen b :action
                (fn [e]
                  (let [v (gui/value lb)]
                    (if (nil? v)
                      (gui/alert e "Oook Oook ! (Il me faut un nombre de cubes à résoudre !)")
                      (let [{mi :min
                             ma :max
                             avg :avg}
                            (results v)]
                        (gui/alert e
                                   (str
                                    "Ook ! min: " mi"\n"
                                    "Ook ! max:" ma"\n"
                                    "Ook ! avg:" avg"\n")))))))
    (-> f gui/pack! gui/show!)))
