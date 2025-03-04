CC = gcc
CFLAGS = -Wall -g
SRCS = args.c
MAIN = main
RM = rm

all: $(MAIN); chmod u+x $(MAIN);

$(MAIN): $(CC) $(CFLAGS) -o $(MAIN) $(SRCS)

clean: $(RM) *.o $(MAIN)