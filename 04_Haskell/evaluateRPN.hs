
import Data.List

main :: IO ()
main = do
   putStrLn "Enter the Reverse Polish Notation string, e.g. ('1 2 + 5 * ==> 15')"
   expression <- getLine
   let tokens = split ' ' expression
   putStrLn $ "Expression is : " ++ expression
   putStrLn $ "Expression split to tokens is : " ++ show(tokens)
   let value = evaluateRPN expression
   putStrLn $ "Expression value is : " ++ show(value)
   
evaluateRPN :: String -> Float
evaluateRPN = head . foldl foldReduce [] . words  
    where   foldReduce (a : b : op) "*" = (a * b) : op
            foldReduce (a : b : op) "+" = (a + b) : op
            foldReduce (a : b : op) "-" = (b - a) : op
            foldReduce (a : b : op) "/" = (b / a) : op
            foldReduce num numberString = read numberString : num


-- Split to words function example   
split :: Char -> String -> [String]
split _ "" = []
split c s = firstWord : (split c rest)
    where firstWord = takeWhile (/=c) s
          rest = drop (length firstWord + 1) s

-- foldl (\acc x -> acc + x) [1..5] -- 15
-- foldl (+x) [1..5] -- 15

-- How it works:
-- * '. words' splits the input to words collection
-- then 5 'terminal' variants of recursion are described : 4 for operation-token and one for a number
-- (a : b : op) means that words in the list are in this order : a, b, op. Depending
--              on the op-code it's transformed into *,+,-,/



