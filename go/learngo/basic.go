package main

import (
	"fmt"
	"math/cmplx"
	"math"
)

var (
	aa  = 3
	ss = "world"
	bb = true
)

func variableZeroVale()  {
	var a int
	var s string
	fmt.Printf("%d  %q\n", a, s)

}
func variableInitialValue()  {
	var a , b int = 3, 4
	var s string = "abc"
	fmt.Println(a, b, s)
}
func variableTypeDeduction()  {
	var a, b, c, s = 3, 4, true, "hello"
	fmt.Println(a, b, c, s)
}

func variableShorter()  {
	a, b, c, s := 4, 8, true, "world"
	fmt.Println(a, b, c, s)
}
func euler()  {
	c := 3 + 4i
	fmt.Println(cmplx.Abs(c))
	d := cmplx.Pow(math.E, 1i * math.Pi)+1
	fmt.Println(d)
	d = cmplx.Exp(1i*math.Pi) +1
	fmt.Println(d)
	fmt.Printf("%3f\n", d)
}
func triangle()  {
	var a, b int = 3, 4
	var c int
	c = int(math.Sqrt(float64(a*a + b*b)))
	fmt.Println(c)
}
func consts()  {
	const filename = "abc.txt"
	const a, b = 3, 4
	var c int
	c = int(math.Sqrt(a*a + b*b))
	fmt.Println(filename, c)
}
func enums()  {
	const (
		cpp = iota
		_
		python
		golang
		javascript
	)
	fmt.Println(cpp, javascript, python, golang)

	const (
		b = 1 << (10 * iota)
		kb
		mb
		gb
		tb
		pb
	)
	fmt.Println(b, kb, mb, gb, tb, pb)

}
func main() {
	fmt.Println("Hello world!")
	variableZeroVale()
	variableInitialValue()
	variableTypeDeduction()
	variableShorter()
	fmt.Println(aa, ss, bb)
	euler()
	triangle()
	consts()
	enums()
}
