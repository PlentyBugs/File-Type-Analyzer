# File-Type-Analyzer
## Run
Run the code with `java Main files patterns.db` where "files" is a directory with the files you would like to check and "patterns.db" is a file with several patterns of files in a special format

## Pattern format
Each pattern should be written on its own line in the format: priority;"pattern";"name of pattern"
An example:
10;"%PDF-";"PDF document"
2;"pmview";"PCP pmview config"
4;"PK";"Zip archive"
