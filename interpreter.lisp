(label null? (lambda (x) (eq nil x)))

(label and? (lambda (x y) 
	(cond (x (cond (y t) (t nil)))
		  (t nil)))