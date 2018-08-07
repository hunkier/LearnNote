package container

import "fmt"

func noRepeatingStringLenght(str string) (len int) {
	lastOccurred := make(map[byte]int)
	start := 0
	maxLenght := 0
	for i, ch := range []byte(str){
		if lastI,ok := lastOccurred[ch]; ok && lastI >= start {
			start = lastI + 1
		}
		if i-start+1 > maxLenght {
			maxLenght = i - start + 1
		}
		lastOccurred[ch] = i
	}
	return  maxLenght
}
func main() {
	fmt.Println(noRepeatingStringLenght("abcbacbabb"))
	fmt.Println(noRepeatingStringLenght("bbbbb"))
	fmt.Println(noRepeatingStringLenght("pwwkew"))
	fmt.Println(noRepeatingStringLenght(""))
	fmt.Println(noRepeatingStringLenght("b"))
	fmt.Println(noRepeatingStringLenght("abcdef"))
}
