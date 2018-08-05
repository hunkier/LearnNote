package main

import (
	"io/ioutil"
	"fmt"
)

func readfile()  {
	const filename = "abc.txt"
	/*
	contents, err := ioutil.ReadFile(filename)
	if err != nil {
		fmt.Println(err)
	}else {
		fmt.Println("%s \n", contents)
	}
	*/
	if contents, err := ioutil.ReadFile(filename); err != nil{
		fmt.Println(err)
	}else{
		fmt.Printf("%s\n", contents)
	}
}
func grade(score int) string {
	g := ""
	switch {
	case score < 0 || score > 100:
		panic(fmt.Sprintf("Wrong score : %d",score))
	case score < 60 :
		g = "F"
	case score < 80:
		g = "C"
	case score < 90:
		g = "B"
	case score <= 100:
		g = "A"
	}
	return g
}
func main() {

	fmt.Println(grade(99))
	
}
