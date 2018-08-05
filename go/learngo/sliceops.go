package main

import "fmt"

func printSlice(arr []int) {
	fmt.Printf("len=%d, cap=%d\n", len(arr), cap(arr))
}
func main() {
	var s []int
	for i := 0; i < 100; i++ {
		printSlice(s)
		s = append(s, 2 * i + 1)
	}
	fmt.Println(s)
}
