(label last 
	(lambda (list)
		(cond ((eq nil (cdr list)) (car list))
			  (#t (last (cdr list))))))

(last (cons (quote a) (cons (quote b) (cons (quote c) nil))))