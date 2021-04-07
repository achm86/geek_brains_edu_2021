/*****************************************************************
	This program takes the input string with arithmetic expression
	and calculates it via Inverse Polish notation approach.
	Acceptable numbers are at most 32-bit integers.
	If result overflows, this will be reported to the output.
	2021-04-06 & [achm86 aka acmdeu]
******************************************************************/

package main

import (
	"fmt"
	"strconv"
)

func read_expression(input_path string) (string, error) {

	// check input size/etc
	err := check_input(input_path)
	if err != nil {
		return "", err
	}

	// read data
	return read_data(input_path)
}

func evaluate_expression(expression string) (int, error) {
	rpn, err := transform_to_rpn(expression)
	if err != nil {
		return 0, err
	}
	fmt.Println("Reverse Polish notation for a given expression is : ")
	for i := 0; i < len(rpn); i++ {
		fmt.Print(rpn[i] + " ")
	}
	fmt.Println("")
	val, err := evaluate_rpn(rpn)
	return val, err
}

func main() {
	expression, err := read_expression(input_file)
	if err != nil {
		fmt.Println(err)
		return
	}

	fmt.Println("Input expression is : " + expression)

	value, err := evaluate_expression(expression)
	if err != nil {
		fmt.Println(err)
		return
	}

	fmt.Println("Expression value is : " + strconv.Itoa(value))
}
