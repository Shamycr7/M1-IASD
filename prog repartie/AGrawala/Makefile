CC=gcc
CFLAGS=-Wall
LDFLAGS=-lpthread
sources= fonction.c main.c 
objets= $(sources:.c=.o)

main: $(objets)
	$(CC) -o $@ $^ $(LDFLAGS)

%.o: %.c
	$(CC) $(CFLAGS) -o $@ -c $<

clean:
	rm  -rf main *.o *~
