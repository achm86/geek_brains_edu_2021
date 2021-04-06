/*****************************************************************
	This program takes the input string with arithmetic expression
	and calculates it via Inverse Polish notation approach.
	Acceptable numbers are at most 32-bit integers.
	If result overflows, this will be reported to the output.
	2021-04-06 & Alexander Chumachenko
******************************************************************/

package main

import (
	"errors"
	"fmt"
	"os"
	"strconv"
)

const input_file string = ".//inputs//input.txt"
const max_expression_length int64 = 512

func get_file_size(filepath string) (int64, error) {

	// get file stats
	file, err := os.Stat(filepath)
	if err != nil {
		return 0, err
	}

	// get the size
	return file.Size(), nil
}

func check_input(input_path string) error {
	// check file and it's size: not more than limit and more than zero
	size, err := get_file_size(input_path)
	if err != nil {
		return err
	}
	if size > max_expression_length {
		return errors.New("expressions longer than 512 characters are not supported")
	}
	if size == 0 {
		return errors.New("file is empty")
	}
	// if all checks passed - return nil == no_error
	return nil
}

func read_data(input_path string) (string, error) {
	// open file
	file, err := os.Open(input_path)
	if err != nil {
		return "", err
	}

	// allocate memory and read the data
	data := make([]byte, max_expression_length)
	len, err := file.Read(data)
	if err != nil {
		return "", err
	}

	// construct resulting string and return
	result := string(data[:len])
	return result, nil
}

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
	return 0, nil
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
