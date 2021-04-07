package main

import (
	"errors"
	"os"
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
