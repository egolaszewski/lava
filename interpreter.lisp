(label caar.
    (lambda (x) (car (car x))))

(label cdar.
    (lambda (x) (cdr (car x))))

(label cadr.
    (lambda (x) (car (cdr x))))

(label cddr.
    (lambda (x) (cdr (cdr x))))

(label cadar.
    (lambda (x) (car (cdr (car x)))))

(label caddr.
    (lambda (x) (car (cdr (cdr x)))))

(label caddar.
    (lambda (x) (car (cdr (cdr (car x))))))

(label null. 
    (lambda (x) (eq x nil)))

(label and.
    (lambda (x y) (cond 
        (x (cond (y #t) (#t nil)))
        (#t nil))))

(label not.
    (lambda (x) (cond
        (x nil)
        (#t #t))))

(label append.
    (lambda (x y) (cond
        ((null. x) y)
        (#t (cons (car x) (append. (cdr x) y))))))

(label list.
    (lambda (x y)
        (cons x (cons y nil))))

(label pair.
    (lambda (x y) (cond
        ((and. (null. x) (null. y)) nil)
        ((and. (not. (atom x)) (not. (atom y)))
            (cons (list. (car x) (car y))
                  (pair. (cdr x) (cdr y)))))))

(label assoc.
    (lambda (x y) (cond
        ((eq (caar. y) x) (cadar. y))
        (#t (assoc. x (cdr y))))))

(label eval.
    (lambda (e a) (cond
        ((atom e) (assoc. e a))
        ((atom (car e)) (cond
            ((eq (car e) (quote quote)) (cadr. e))
            ((eq (car e) (quote atom))  (atom      (eval. (cadr. e) a)))
            ((eq (car e) (quote eq))    (eq        (eval. (cadr. e) a)
                                                   (eval. (caddr. e) a)))
            ((eq (car e) (quote car))   (car       (eval. (cadr. e) a)))
            ((eq (car e) (quote cdr))   (cdr       (eval. (cadr. e) a)))
            ((eq (car e) (quote cons))  (cons      (eval. (cadr. e) a)
                                                   (eval. (caddr. e) a)))
            ((eq (car e) (quote cond))  (evcon.    (cdr e) a))
            (#t (eval. (cons (assoc. (car e) a)
                             (cdr e)) a))))
    ((eq (caar e) (quote label)) 
        (eval. (cons (caddar. e) (cdr e))
               (cons (list. (cadar. e) (car e)) a)))
    ((eq (caar e) (quote lambda))
        (eval. (caddar e)
               (append. (pair. (cadar e) (evlis. (cdr e) a)) a))))))

(label evcon.
    (lambda (c a) (cond
        ((eval. (caar. c) a)
         (eval. (cadar. c) a))
        (#t (evcon. (cdr c) a)))))

(label evlis.
    (lambda (m a) (cond
        ((null. m) nil)
        (#t (cons (eval.    (car m) a)
                  (evlis.   (cdr m) a))))))

(eval. (quote (cons x (quote (b c)))) (quote ((x a) (y b))))