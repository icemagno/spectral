# http://flowingdata.com/2012/12/17/getting-started-with-charts-in-r/
# http://stackoverflow.com/questions/17721126/simplest-way-to-do-grouped-barplot

#mydata <- read.csv2(file="D:/R-Stuff/sagi_input.txt",head=TRUE,sep=";")
source('bar.r')
outfile <- paste(output_folder, "r-chart.png", sep="")
png(filename=outfile, height=350, width=900, bg="white")
bar(dv = qtd_aptos, factors = c(faixa_etaria, uf), dataframe = csv_data, errbar = FALSE ) 
dev.off()