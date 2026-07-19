CC=gcc
TARGET=main
SRC=src
BIN=bin
OBJ=obj
INCLUDE=$(SRC)
default: $(BIN)/$(TARGET) 

$(BIN)/$(TARGET): 			$(OBJ)/$(TARGET).o  \
							$(OBJ)/element_buffer.o $(OBJ)/array_buffer.o  $(OBJ)/vertex_buffer.o \
							$(OBJ)/shader.o $(OBJ)/texture.o \
							$(OBJ)/camera.o $(OBJ)/mesh.o $(OBJ)/light.o \
							$(OBJ)/box.o $(OBJ)/player.o \
							$(OBJ)/physic.o \
							$(OBJ)/material.o \
							$(OBJ)/portal.o \
							$(OBJ)/glad.o
	$(CC) $^ $(LINK) -o $@

$(OBJ)/$(TARGET).o: $(SRC)/$(TARGET).c
	$(CC) -I $(INCLUDE) $^ -c -g -o $@

$(OBJ)/%.o: $(SRC)/%.c $(SRC)/%.h
	$(CC) -I $(INCLUDE) $< -c -g -o $@

$(OBJ)/glad.o: $(SRC)/glad.c 
	$(CC) -I $(INCLUDE) $< -c -g -o $@

clean:
	rm -f $(OBJ)/*.o 
	rm -f $(BIN)/* 
