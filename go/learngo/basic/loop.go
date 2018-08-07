package basic

import (
	"strconv"
	"fmt"
)

func convertToBin(n int) string {
	result := ""
	for ; n >0 ; n /= 2{
		lsb := n % 2
		result = strconv.Itoa(lsb) + result
	}
	return result;
}

func forever()  {
	for   {
		fmt.Println("golang")
	}
}
func main() {
	fmt.Println(
		convertToBin(5),
		convertToBin(13),
		)

	forever()
}
