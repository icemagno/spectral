insert into union_out 
	(id_pipeline, id_experiment, id_activity, caixa1, gvfile, optifunc, evaluatedvalue, g6fileid) 
select %ID_PIP%, %ID_EXP%, %ID_ACT%, graphviz_out.caixa1, graphviz_out.gvfile, evaluate_out.optifunc, evaluate_out.evaluatedvalue, 
	evaluate_out.g6fileid from graphviz_out, evaluate_out 
where evaluate_out.g6fileid = graphviz_out.g6fileid and graphviz_out.id_experiment = %ID_EXP%