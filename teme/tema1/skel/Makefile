# APD - Tema 1
# Octombrie 2021

build:
	@echo "Building..."
	@gcc -o tema1 tema1.c genetic_algorithm.c -lm -Wall -Werror
	@echo "Done"

build_debug:
	@echo "Building debug..."
	@gcc -o tema1 tema1.c genetic_algorithm.c -lm -Wall -Werror -O0 -g3 -DDEBUG
	@echo "Done"

clean:
	@echo "Cleaning..."
	@rm -rf tema1
	@echo "Done"
