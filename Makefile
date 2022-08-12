SBT = sbt
SOURCE = source
OPEN = open
CURR_DIR:=$(shell pwd)

SSH_FILE := ~/dev/chisel/setup/ssh.txt 
PORT_FILE := ~/dev/chisel/setup/port.txt
REMOTE_SSH := $(shell cat ${SSH_FILE})
REMOTE_PORT := $(shell cat ${PORT_FILE})

# 서버상의 Vivado 프로젝트의 verilog 파일이 저장되는 디렉토리를 설정
REMOTE_PROJ_DIR := ~/Documents/dev/

#######################
# Generate Verilog Code
add:
	$(SBT) "runMain empty.ALUMain" 

##############
# Run the test
addTest:
	$(SBT) "testOnly AddTest"

##################################
# Draw Timing Diagram with GTKWave
addWave:
	$(OPEN) ./test_run_dir/Add_should_pass/*.vcd

####################
# Draw Block Diagram
addDiagrammer:
	$(SOURCE) ./util/draw_diagrammer.sh -f "Add"

#########
# Clean
clean:
	rm -rf generated
	rm -rf project
	rm -rf target

########
# Drawio
drawio:
	cd util \
		&& $(SOURCE) ./drawio.sh \

drawioNew:
	@read -p "Enter File Name:" file_name; \
	cp ./blank.drawio ./diagram/$$file_name.drawio \
	&& open ./diagram/$$file_name.drawio

########
# Wavedrom
wavedrom:
	cd util \
		&& $(SOURCE) ./wavedrom.sh \

##########
# ScalaDoc
doc:
	make docExport \
		&& make drawio \
		&& make wavedrom \
		&& cp -r -f $(CURR_DIR)/src/main/drawio/ $(CURR_DIR)/target/scala-2.12/api/ \
		&& cp -r -f $(CURR_DIR)/src/main/wavedrom/ $(CURR_DIR)/target/scala-2.12/api/ \
		&& find $(CURR_DIR)/src/main/drawio/ -type f -name "*.svg" -exec rm {} \; \
		&& find $(CURR_DIR)/src/main/wavedrom/ -type f -name "*.svg" -exec rm {} \;
docExport:
	$(SBT) doc
docOpen:
	open ./target/scala-2.12/api/index.html

help:
	echo "사용방법" && echo "tkdyfjasdkfj"
