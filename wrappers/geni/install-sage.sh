#!/bin/bash

apt-add-repository -y ppa:aims/sagemath
apt-get update
apt-get install sagemath-upstream-binary


Instalando o MathChem no Portal Espectral
Faça o Download do arquivo **mathchem-1.1.3.spkg** no endereço
http://mathchem.iam.upr.si/downloads/mathchem-1.1.3.spkg
Salvar o arquivo no diretório do SAGE
Rodar o sage com o comando para instalar 
sage -f mathchem-1.1.3.spkg


root@rukbat1:/usr/lib/sagemath# cp /home/sagitarii/mathchem-1.1.3.spkg .
root@rukbat1:/usr/lib/sagemath# sage -f mathchem-1.1.3.spkg
/usr/lib/sagemath/build/make/pipestatus: line 39: sage-spkg: command not found
