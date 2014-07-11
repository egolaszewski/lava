(label null (lambda (x) (eq nil x)))

(label and (lambda (x y)
	(cond (x (cond (y t) (t nil)))
		  (t nil))))

(label not (lambda (x) 
	(cond (x nil)
		  (t t))))
		  
(label append (lambda (x y)
	(cond ((null x) y)
		  (t (cons (car x) (append (cdr x) y))))))
		
(null t)
(null nil)

(and t t)
(and t nil)
(and nil t)

(not t)
(not nil)

(cons (quote a) (quote b))