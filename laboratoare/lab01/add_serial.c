#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

/*
    implementare seriala pentru exercitiul 5
*/

int *arr;
int array_size;

int main(int argc, char *argv[]) {
  if (argc < 2) {
    fprintf(stderr, "Specificati dimensiunea array-ului\n");
    exit(-1);
  }

  array_size = atoi(argv[1]);

  arr = malloc(array_size * sizeof(int));
  for (int i = 0; i < array_size; i++) {
    arr[i] = i;
  }

  for (int i = 0; i < array_size; i++) {
    printf("%d", arr[i]);
    if (i != array_size - 1) {
      printf(" ");
    } else {
      printf("\n");
    }
  }

  // Vom folosi varianta implementata serial ca referinta (baseline) pentru calculul acceleratiei (speedup-ului)
  for (int i = 0; i < array_size; i++) {
    arr[i] += 100;
  }

  for (int i = 0; i < array_size; i++) {
    printf("%d", arr[i]);
    if (i != array_size - 1) {
      printf(" ");
    } else {
      printf("\n");
    }
  }

  return 0;
}
