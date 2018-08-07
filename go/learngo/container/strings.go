package main

import (
	"fmt"
	"unicode/utf8"
)

func main() {
	s := "Yes我爱慕课网!"
	fmt.Println(s)

	for _, b := range []byte(s){
		fmt.Printf("%X ",b)
	}

	fmt.Println()

	for i, ch := range s {  // ch is a rune
		fmt.Printf("(%d %X)", i, ch)
	}
	fmt.Println()

	fmt.Println("Rune count:",
		utf8.RuneCountInString(s))

	bytes := []byte(s)
	for len(bytes) > 0 {
		ch, size := utf8.DecodeRune(bytes)
		fmt.Printf("%c ", ch)
		bytes = bytes[size:]
	}
	fmt.Println()
	
}
