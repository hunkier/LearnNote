package main

import (
	"fmt"
	"reflect"
	"runtime"
	"math"
)

func eval(a, b int, op string) (int, error ){
	switch op {
	case "+":
		return a + b, nil
	case "-":
		return a - b, nil
	case "*":
		return a * b, nil
	case "/":
		q , _ := div(a, b)
		return q, nil
	default:
		return  0 ,fmt.Errorf("unsupported operation: %s" , op)
	}
}
func div(a, b int) (q, r int) {
	return a / b, a % b
}
func apply(op func(int, int) int, a, b int) int {
	p := reflect.ValueOf(op).Pointer()
	opName := runtime.FuncForPC(p).Name()
	fmt.Printf("Calling function %s with args "+
		"(%d, %d) ", opName, a, b)
	return op(a, b)
}
func pow(a, b int) int {
	return int(math.Pow(float64(a), float64(b)))
}
func sum(numbers ...int) int {
	sum := 0
	for i := range numbers {
		sum += numbers[i]
	}
	return sum
}

func swap(a, b int) (int, int) {
	return b , a
}
func swapPointer(a, b *int) {
	*a, *b = *b, *a
}
func main() {
	fmt.Println(eval(1, 2 , "+"))
	fmt.Println(eval(13, 2 , "/"))
	fmt.Println(eval(13, 2 , "X"))
	fmt.Println(div(17, 5))
	fmt.Println(apply(pow, 2, 2))
	fmt.Println(apply(func(a int, b int) int {
		return int(math.Pow(float64(a), float64(b)))
	}, 2, 3))
	fmt.Println(sum(1, 2, 3, 4, 5, 6 ))


	a, b := 3, 4
	a, b = swap(a, b)
	fmt.Println(a,b)
	swapPointer(&a, &b)
	fmt.Println(a, b)
}
