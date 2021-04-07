package main

import (
	"errors"
	"math"
	"strconv"
)

func is_digit(val byte) bool {
	return val >= '0' && val <= '9'
}

func op_prio(op string) int {
	if op[0] == '+' || op[0] == '-' {
		return 3
	}
	if op[0] == '/' || op[0] == '*' {
		return 2
	}
	if op[0] == '(' || op[0] == ')' {
		return 0
	}
	return -1
}

func transform_to_rpn(expression string) ([]string, error) {
	var rpn []string
	var op_stack string
	var number string

	for i := 0; i < len(expression); i++ {
		if is_digit(expression[i]) {
			number += string(expression[i])
		} else {
			if len(number) > 0 {
				rpn = append(rpn, number)
				number = ""
			}
			if expression[i] == '(' {
				op_stack += string(expression[i])
			} else if expression[i] == ')' {
				var correct bool = false
				for j := len(op_stack) - 1; j >= 0; j-- {
					if op_stack[j] == '(' {
						op_stack = op_stack[:len(op_stack)-1] // remove '('
						correct = true
						break
					}
					rpn = append(rpn, string(op_stack[j]))
					op_stack = op_stack[:len(op_stack)-1] // pop back last character
				}
				if !correct {
					var ans []string
					return ans, errors.New("input string had incorrect format")
				}
			} else if expression[i] == ' ' {
				// just skip spaces
			} else {
				// and push all operations to the stack which are at least prio in comparison to this one
				if !is_operation(string(expression[i])) {
					var ans []string
					return ans, errors.New("input string had incorrect format : invalid operation")
				}
				this_prio := op_prio(string(expression[i]))
				for j := len(op_stack) - 1; j >= 0 && op_stack[j] != '('; j-- {
					if this_prio >= op_prio(string(op_stack[j])) {
						rpn = append(rpn, string(op_stack[j]))
						op_stack = op_stack[:len(op_stack)-1] // pop back last character
					}
				}
				op_stack += string(expression[i])
			}
		}
	}

	if len(number) > 0 {
		rpn = append(rpn, number)
		number = ""
	}

	// finally add all operations to the rpn
	for j := len(op_stack) - 1; j >= 0; j-- {
		rpn = append(rpn, string(op_stack[j]))
	}

	return rpn, nil
}

func is_operation(val string) bool {
	if len(val) == 1 {
		if val[0] == '+' || val[0] == '-' || val[0] == '*' || val[0] == '/' {
			return true
		}
	}
	return false
}

func apply_operation(lhs int64, rhs int64, op string) (int64, error) {
	if op == "+" {
		return int64(lhs) + int64(rhs), nil
	} else if op == "-" {
		return int64(lhs) - int64(rhs), nil
	} else if op == "*" {
		return int64(lhs) * int64(rhs), nil
	} else if op == "/" {
		if rhs == 0 {
			return math.MaxInt64, errors.New("division by zero")
		}
		return int64(lhs) / int64(rhs), nil
	}
	return 0, errors.New("unexpected operation : " + op)
}

func evaluate_rpn(rpn []string) (int, error) {
	var values []int64
	for i := 0; i < len(rpn); i++ {
		if is_operation(rpn[i]) {
			if len(values) < 2 {
				return 0, errors.New("incorrect expression format")
			}
			var lhs, rhs int64 = values[len(values)-2], values[len(values)-1]
			val, err := apply_operation(lhs, rhs, rpn[i])
			if err != nil {
				return 0, nil
			}
			values = values[:len(values)-1]
			values[len(values)-1] = val
		} else {
			val, err := strconv.Atoi(rpn[i])
			if err != nil {
				return 0, err
			}
			values = append(values, int64(val))
		}
	}
	if len(values) != 1 {
		return 0, errors.New("incorrect expression format")
	}
	if values[0] > int64(math.MaxInt32) || values[0] < int64(math.MinInt32) {
		return 0, errors.New("expression value overflowed")
	}
	return int(values[0]), nil
}
