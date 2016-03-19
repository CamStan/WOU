{-
    Cameron Stanavige
    CS 262
    Lab 8 - Question 3
-}

import System.Environment (getArgs)
import Data.List (intercalate)

main = do
    args <- getArgs
    let values = map read args :: [Integer] -- turns args into Integers
    mapM_ putStrLn $ formatAnswers values -- pairs and prints values w/ factors
                   $ formatFactors -- formats factors into multiples
                   $ factorsToStrings -- turns list of factors into Strings
                   $ getFactors values -- gets list of factors for each value

-- Test for whole division
divides :: Integer -> Integer -> Bool
divides d n = rem n d == 0

{-
    Find the time factors of a number, including multiplicity. Returns a list
    you can multiply together to get the original number,
    i.e. product (factors 55) == 55
-}
factors :: Integer -> [Integer]
factors n
    | n < 1     = error "Only non-negative integers allowed"
    | n == 1    = [1]
    | otherwise = factorHelp n 2

-- Helper recursive function
factorHelp :: Integer -> Integer -> [Integer]
factorHelp n x
    | n == x    = [n]
    | otherwise = if divides x n
                  then
                      x : (factorHelp (div n x) x)
                  else
                      factorHelp n (x+1)

-- generates all the factors of the input list of Integers
getFactors :: [Integer] -> [[Integer]]
getFactors = map factors

-- turns each factors iin the list of factors into a string
factorsToStrings :: Show a => [[a]] -> [[String]]
factorsToStrings fs = [map show x | x <- fs]

-- formats the factors into multiples (e.g. ["3","17"] == "3 x 17")
formatFactors:: [[String]] -> [String]
formatFactors = map (intercalate " x ")

-- places each input value with it's formated factors
formatAnswers :: Show a => [a] -> [String] -> [String]
formatAnswers vals facts = zipWith (\x y -> show x ++ " == " ++ y) vals facts


-- mapM_ putStrLn (map (printnice . factors . read) args)
