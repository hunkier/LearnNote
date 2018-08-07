package container

import "fmt"

func main() {

	m1 := map[string]string{
		"Traversing":"map",
		"name":"hunkier",
		"course": "golang",
		"quality": "notbad",
	}
	fmt.Println(m1)

	m2 := make(map[string]int) // m2 == empty map
	fmt.Println(m2)

	var m3 map[string]int // m3 == nil
	fmt.Println(m3)
	fmt.Println("Traversin map ------")
	for k, v := range m1 {
		fmt.Println(k,v)
	}

	fmt.Println("Getting value")
	courseName, ok := m1["course"]
	fmt.Println(courseName, ok)
	if causeName, ok := m1["cause"]; ok {
		fmt.Println(causeName)
	}else {
		fmt.Println("key does not exist ")
	}
	fmt.Println("Delete value")
	name, ok := m1["name"]
	fmt.Println(name,ok)
	delete(m1, "name")
	name, ok = m1["name"]
	fmt.Println(name, ok)

}
