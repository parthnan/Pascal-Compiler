package enshud.s3.checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
//System.out.println("--------------JANNAT  ");
public class Checker {
	private static final int DEBUG = 0;
	private static final int SEMDEBUG = 0;
	String[][] tokens = {
		      {"0","and","SAND"}, 
		      {"1","array","SARRAY"},
		      {"2","begin","SBEGIN"},
		      {"3","boolean","SBOOLEAN"},
		      {"4","char","SCHAR"},
		      {"5","div","SDIVD"},
		      {"6","do","SDO"},
		      {"7","else","SELSE"},
		      {"8","end","SEND"},
		      {"9","false","SFALSE"},
		      
		      {"10","if","SIF"}, 
		      {"11","integer","SINTEGER"},
		      {"12","mod","SMOD"},
		      {"13","not","SNOT"},
		      {"14","of","SOF"},
		      {"15","or","SOR"},
		      {"16","procedure","SPROCEDURE"},
		      {"17","program","SPROGRAM"},
		      {"18","readln","SREADLN"},
		      {"19","then","STHEN"},
		      
		      {"20","true","STRUE"}, 
		      {"21","var","SVAR"},
		      {"22","while","SWHILE"},
		      {"23","writeln","SWRITELN"},
		      {"24","=","SEQUAL"},
		      {"25","<>","SNOTEQUAL"},
		      {"26","<","SLESS"},
		      {"27","<=","SLESSEQUAL"},
		      {"28",">=","SGREATEQUAL"},
		      {"29",">","SGREAT"},
		      
		      {"30","+","SPLUS"}, 
		      {"31","-","SMINUS"},
		      {"32","*","SSTAR"},
		      {"33","(","SLPAREN"},
		      {"34",")","SRPAREN"},
		      {"35","[","SLBRACKET"},
		      {"36","]","SRBRACKET"},
		      {"37",";","SSEMICOLON"},
		      {"38",":","SCOLON"},
		      {"39","..","SRANGE"},
		      
		      {"40",":=","SASSIGN"}, 
		      {"41",",","SCOMMA"},
		      {"42",".","SDOT"},
		   
		      {"5","/","SDIVD"},
		};
	
	
	int[][] syntax = new int[][]{
		  { 17, 43, 37, 101, 133, 42, -1,-1,		-1 },
		  { 102, 109, -1, -1, -1, -1, -1, -1,		-1 },
		  { 21, 103, -1, -1, -1, -1, -1, -1,		-10 },
		  { 104, 38, 105, 37, -1, -1, -1, -1,		100 },
		  { 43, -1, -1, -1, -1, -1, -1,	-1,		98 },
		  //105
		  { 106, 107, -1, -1, -1, -1, -1, -1,		-100 },
		  { 3, 4, 11, -1, -1, -1, -1, -1,		-100 },
		  { 1, 35, 108, 39, 108, 36, 14, 106,		-1 },
		  { 134, 135, 44, -1, -1, -1, -1, -1,		-100 },
		  { 110, 37, -1, -1, -1, -1, -1, -1,		0 },
		  //110
		  { 111, 102, 133, -1, -1, -1, -1, -1,		-1 },
		  { 16, 43, 112, 37, -1, -1, -1, -1,		-1 },
		  { 33, 113, 34, -1, -1, -1, -1, -1,		-10 },
		  { 114, 38, 106, -1, -1, -1, -1, -1,		99 },
		  { 43, -1, -1, -1, -1, -1, -1, -1,		98 },
		  //115
		  { 116, 37, -1, -1, -1, -1, -1, -1,		0 },
		  { 138, 136, 137, 117, -1, -1, -1, -1,		-100 },
		  { 118, 121, 130, 133, -1, -1, -1, -1,		-100 },
		  { 119, 40, 123, -1, -1, -1, -1, -1,		-1 },
		  { 120, 43, -1, -1, -1, -1, -1, -1,		-100 },
		  //120
		  { 43, 35, 123, 36, -1, -1, -1, -1,	-1 },
		  { 139, 43, -1, -1, -1, -1, -1, -1,		-100 },
		  { 123, -1, -1, -1, -1, -1, -1, -1,		98 },
		  { 140, 124, -1, -1, -1, -1, -1, -1,		-100 },
		  { 143, 144, 142, -1, -1, -1, -1, -1,		-100 },
		  //125
		  { 126, 145, -1, -1, -1, -1, -1, -1,		-1 },
		  { 119, 132, 146, 147, -1, -1, -1, -1,		-100 },
		  { 24, 25, 27, 28, 29, 26, -1, -1,		-100 },
		  { 30, 31, 15, -1, -1, -1, -1, -1,		-100 },
		  { 5, 32, 12, 0, -1, -1, -1, -1,		-100 },
		  //130
		  { 148, 149, 18, 23, -1, -1, -1, -1,		-100 },
		  { 119, -1, -1, -1, -1, -1, -1, -1,	98 },
		  { 44, 45, 9, 20, -1, -1, -1, -1,		-100 },
		  
		  //FUKUGOUBUN = begin narabi end - 133
		  { 2, 115, 8, -1, -1, -1, -1, -1,		-1 },
		  
		  //EXTRA CASES START HERE from 134
		  { 30, 44, -1, -1, -1, -1, -1, -1,		-1 },
		  { 31, 44, -1, -1, -1, -1, -1, -1,		-1 },
		  { 10, 123, 19, 133, 7, 133, -1, -1,		-1 },
		  { 10, 123, 19, 133, -1, -1, -1, -1,		-1 },
		  { 22, 123, 6, 133, -1, -1, -1, -1,		-1 },
		  
		  { 43, 33, 122, 34, -1, -1, -1, -1,		-1 },
		  { 124, 127, 124, -1, -1, -1, -1, -1,		-1 },
		  { 128, 125, -1, -1, -1, -1, -1, -1,		0 },
		  { 125, 141, -1, -1, -1, -1, -1, -1,		-1 },
		  { 30, 125, 141, -1, -1, -1, -1, -1,		-1 },
		  
		  { 31, 125, 141, -1, -1, -1, -1, -1,		-1 },
		  { 129, 126, -1, -1, -1, -1, -1, -1,		0 },
		  { 33, 123, 34, -1, -1, -1, -1, -1,		-1 },
		  { 13, 126, -1, -1, -1, -1, -1, -1,		-1 },
		  { 18, 33, 131, 34, -1, -1, -1, -1,		-1 },
		  
		  { 23, 33, 122, 34, -1, -1, -1, -1,		-1 },
	};
	/**
	 * サンプルmainメソッド．
	 * 単体テストの対象ではないので自由に改変しても良い．
	 */
	public static void main(final String[] args) {
		// normalの確認
		new Checker().run("data/ts/synerr08.ts");
		//new Checker().run("data/ts/normal12.ts");

		// synerrの確認
		//new Checker().run("data/ts/synerr08.ts");
		//new Checker().run("data/ts/synerr02.ts");

		// semerrの確認
		//new Checker().run("data/ts/semerr01.ts");
		//new Checker().run("data/ts/semerr02.ts");
	}
	
	public void debug(final String message) {
		if(DEBUG==1) {System.out.println(message);}
	}
	
	public void checkerdebug(final String message) {
		if(SEMDEBUG==1) {System.out.println(message);}
	}
	
	public void report_semantic(int address) {
		if(line_of_semantic==0) {line_of_semantic=Integer.parseInt(line.get(address));}
	}
	
	ArrayList<String> program = new ArrayList<String>();
	ArrayList<String> line = new ArrayList<String>();
	ArrayList<String> literal = new ArrayList<String>();
	ArrayList<String> vars = new ArrayList<String>();
	ArrayList<String> vartypes = new ArrayList<String>();
	ArrayList<String> varset = new ArrayList<String>();
	ArrayList<String> varproc = new ArrayList<String>();
	String currentproc = "main";
	String prevproc = "";
	public int line_of_last_true = 1;
	public int line_of_semantic = 0;
	
	public void run(final String inputFileName) {

		try  {
			String content = new Scanner(new File(inputFileName)).useDelimiter("\\Z").next();
			StringTokenizer lines = new StringTokenizer(content, "\n");
			while (lines.hasMoreTokens()) {
				StringTokenizer words = new StringTokenizer(lines.nextToken(), "\t") ;		
				String next=words.nextToken(); 		literal.add(next);
				next=words.nextToken(); 	
				next=words.nextToken();  	program.add(next);
				next=words.nextToken();  	line.add(next);
			}
			
			int syntaxerror=search(100,0);
			
			//System.out.println(literal);System.out.println(varproc);
			
			if(syntaxerror>=0 && line_of_semantic==0) {System.out.println("OK");}
			else if(syntaxerror<0){
				if(line_of_last_true<line_of_semantic && line_of_last_true>0 || line_of_semantic==0 && line_of_last_true>0) {
					System.err.println("Syntax error: line "+line_of_last_true);
				}
				else if(line_of_last_true>=line_of_semantic && line_of_semantic>0 || line_of_semantic>0 && line_of_last_true==0) {
					System.err.println("Semantic error: line "+line_of_semantic);
				}
			}
			else if(line_of_semantic>0) {System.err.println("Semantic error: line "+line_of_semantic);}
        }
    	catch (FileNotFoundException b) {System.err.println("File not found");}

	}
	
	String exptype(int offset,int end) {
		if(end-offset<1) {checkerdebug(line.get(offset)+" | "+(offset+1)+" , "+(end+1)+" , "+literal.get(offset));
			if(program.get(offset).equals("44")) {
				if(Integer.parseInt(literal.get(offset))<=32768&&literal.get(offset-1)=="-") {return "integer";}  
				else if(Integer.parseInt(literal.get(offset))<=32767&&literal.get(offset-1)!="-") {return "integer";}  
				else {line_of_semantic=Integer.parseInt(line.get(offset));	return "ERROR";} // integer OUT OF BOUNDS ERROR
			}
			else if(program.get(offset).equals("45")) {
				int length=literal.get(offset).length()-2; 
				if(length>1) {return "char [1.."+length+"]";}	else {return "char";}
			}
			else if(literal.get(offset).equals("false")||literal.get(offset).equals("true")) {return "boolean";}
			else if(vars.indexOf(literal.get(offset))==-1) {
				line_of_semantic=Integer.parseInt(line.get(offset));	//UNDECLARED VARIABLE ERROR(not declared anywhere)
				return "ERROR";
			}
			else if(!varproc.get(vars.indexOf(literal.get(offset))).equals("main") && !varproc.get(vars.indexOf(literal.get(offset))).equals(currentproc)) {
				line_of_semantic=Integer.parseInt(line.get(offset));	//VARIABLE OUT OF SCOPE ERROR
				return "ERROR";
			}
			else if(Boolean.getBoolean(varset.get(vars.indexOf(literal.get(offset))))) {
				line_of_semantic=Integer.parseInt(line.get(offset));	//UNASSIGNED VARIABLE ERROR
				return "ERROR";
			}
			else if(currentproc.equals("main")&&varproc.get(vars.indexOf(literal.get(offset))).equals("main")){return vartypes.get(vars.indexOf(literal.get(offset)));}   
			//retreive main procedure variables
			else {				//retreive subprocedure variables
				if(varproc.get(vars.indexOf(literal.get(offset))).equals(currentproc)) {return vartypes.get(vars.indexOf(literal.get(offset)));}
				else if(varproc.get(vars.indexOf(literal.get(offset))).equals("main")) {return vartypes.get(vars.indexOf(literal.get(offset)));}
			}
			line_of_semantic=Integer.parseInt(line.get(offset));	//UNDECLARED VARIABLE ERROR(not declared in main or current procedure)
			return "ERROR";
		}
		else {checkerdebug(line.get(offset)+" | "+(offset+1)+" , "+(end+1)+" , ");
			int hassign=0;
			int i=0,turnoff=0;
			String temp;    //SHIKI -> TANJUNSHIKI KANKEI TANJUNSHIKI
			while(offset+i!=end&&(turnoff!=0||(!literal.get(offset+i).equals("=")&&!literal.get(offset+i).equals("<>")&&!literal.get(offset+i).equals("<")&&!literal.get(offset+i).equals(">")&&!literal.get(offset+i).equals("<=")&&!literal.get(offset+i).equals(">="))))
			{	
				if(literal.get(offset+i).equals("[")){	turnoff+=1;		}
				else if(literal.get(offset+i).equals("(")) {	turnoff-=1;		}
				else if(literal.get(offset+i).equals(")")&&turnoff<0) {	turnoff+=1;		}
				else if(literal.get(offset+i).equals("]")&&turnoff>0) {	turnoff-=1;		}
				i++;	
			}
			if(offset+i!=end) {
				temp=exptype(offset,offset+i-1);
				if(temp.equals(exptype(offset+i+1,end))) {return "boolean";}
			}
			else {			//TANJUNSHIKI -> KOU
				i=0;turnoff=0;
				if(literal.get(offset).equals("+")||literal.get(offset).equals("-")) {hassign=1;i++;}
				while(offset+i!=end&&(turnoff!=0||(!literal.get(offset+i).equals("+")&&!literal.get(offset+i).equals("-")&&!literal.get(offset+i).equals("or"))))
				{	
					if(literal.get(offset+i).equals("[")){	turnoff+=1;		}
					else if(literal.get(offset+i).equals("(")) {	turnoff-=1;		}
					else if(literal.get(offset+i).equals(")")&&turnoff<0) {	turnoff+=1;		}
					else if(literal.get(offset+i).equals("]")&&turnoff>0) {	turnoff-=1;		}
					i++;	
				}
				if(offset+i!=end&&literal.get(offset+i).equals("or")) {
					temp=exptype(offset,offset+i-1);
					if(temp.equals(exptype(offset+i+1,end))&&temp.equals("boolean")) {return "boolean";}
				}
				else if(offset+i!=end) {
					temp=exptype(offset,offset+i-1);
					if(temp.equals(exptype(offset+i+1,end))&&temp.equals("integer")) {return "integer";}
				}
				else {			//KOU -> INSHI
					i=hassign;turnoff=0;
					while(offset+i!=end&&(turnoff!=0||(!literal.get(offset+i).equals("/")&&!literal.get(offset+i).equals("div")&&!literal.get(offset+i).equals("*")&&!literal.get(offset+i).equals("and")&&!literal.get(offset+i).equals("mod"))))
					{	
						if(literal.get(offset+i).equals("[")){	turnoff+=1;		}
						else if(literal.get(offset+i).equals("(")) {	turnoff-=1;		}
						else if(literal.get(offset+i).equals(")")&&turnoff<0) {	turnoff+=1;		}
						else if(literal.get(offset+i).equals("]")&&turnoff>0) {	turnoff-=1;		}
						i++;	
					}
					if(offset+i!=end&&literal.get(offset+i).equals("and")) {
						temp=exptype(offset,offset+i-1);
						if(temp.equals(exptype(offset+i+1,end))&&temp.equals("boolean")) {return "boolean";}
					}
					else if(offset+i!=end) {
						temp=exptype(offset,offset+i-1);
						if(temp.equals(exptype(offset+i+1,end))&&temp.equals("integer")) {return "integer";}
					}
					else {			//INSHI -> HENSUU|(SHIKI)|NOT INSHI
						if(literal.get(offset+hassign).equals("not")) {
							if(exptype(offset+hassign+1,end).equals("boolean")) {return "boolean";}
						}
						else if(literal.get(offset+hassign).equals("(")) {
							return exptype(offset+1+hassign,end-1);
						}
						else {      		//HENSUU/TEISUU
							if(literal.get(offset+1+hassign).equals("[")) {
								int j=hassign;
								while(!literal.get(offset+j).equals("]"))
								{	j++;	}
								if(exptype(offset+2+hassign,offset+j-1).equals("integer")) {			//ARRAY ELEMENT HENSUU/TEISUU
									StringTokenizer tokens = new StringTokenizer(vartypes.get(vars.indexOf(literal.get(offset+hassign))));
									return tokens.nextToken();
								}
								else {line_of_semantic=Integer.parseInt(line.get(offset)); return "ERROR";}  //SOEJI NOT INTEGER ERROR
							}
							else{return exptype(offset+hassign,offset+hassign);}     //NON ARRAY ELEMENT HENSUU/TEISUU
						}
					}
				}
			}
		}
		line_of_semantic=Integer.parseInt(line.get(offset));	
		return "ERROR";
	}

	
	boolean storage(int target,int offset,int end) {//checkerdebug(target+","+offset+","+end);
		int num=0,j=0,k=0;
		switch(target) {
			case 103:
				while(offset+j<=end-3){
					num=0;
					while(!literal.get(offset+j).equals(":")) {
						if(program.get(offset+j).equals("43")) {			
							if(vars.contains(literal.get(offset+j))) 
							{
								if(varproc.get(vars.indexOf(literal.get(offset+j))).equals(currentproc)) 
								{	line_of_semantic=Integer.parseInt(line.get(offset+j));	return false;	}		//DUPLICATE DECLARATION ERROR
							}
							else{
								vars.add(literal.get(offset+j));		varproc.add(currentproc);
								varset.add("false");num++;
							}
						}
						j++;
					}
					j++;
					for(int i=0;i<num;i++) {
						if(literal.get(offset+j+1).equals("[")) {
							vartypes.add(literal.get(offset+j+7)+" "+literal.get(offset+j+1)+literal.get(offset+j+2)+literal.get(offset+j+3)+literal.get(offset+j+4)+literal.get(offset+j+5));
							for(k=Integer.parseInt(literal.get(offset+j+2));k<=Integer.parseInt(literal.get(offset+j+4));k++) {
								vars.add(vars.get(vars.size()-1-k+Integer.parseInt(literal.get(offset+j+2)))+"["+k+"]");		varproc.add(currentproc);	
								varset.add("true");		vartypes.add(literal.get(offset+j+7));
							}
						}
						else{vartypes.add(literal.get(offset+j));}
					}
					while(!literal.get(offset+j).equals(";")) {j++;}
					j++;
				}
				return true;
				
			case 118:    				//TO MARK AS ASSIGNED 
				if(vars.indexOf(literal.get(offset))==-1) {
					line_of_semantic=Integer.parseInt(line.get(offset));	//UNDECLARED VARIABLE ERROR
					return false;
				}
				if(!varproc.get(vars.indexOf(literal.get(offset))).equals("main") && !varproc.get(vars.indexOf(literal.get(offset))).equals(currentproc)) {
					line_of_semantic=Integer.parseInt(line.get(offset));	//VARIABLE OUT OF SCOPE ERROR
					return false;
				}
				if(vartypes.get(vars.indexOf(literal.get(offset))).length()>7 && !literal.get(offset+1).equals("[")) {
					line_of_semantic=Integer.parseInt(line.get(offset));	//ARRAY IN LHS ERROR
					return false;
				}
				k=0;
				while(!literal.get(offset+k).equals(":="))
				{	k++;	}
				if(!exptype(offset+k+1,end).equals(exptype(offset,offset+k-1))) {
					line_of_semantic=Integer.parseInt(line.get(offset));	//TYPE MISMATCH ERROR
					return false;
				} 
				if(literal.get(offset+1).equals(":=")) { varset.set(vars.indexOf(literal.get(offset)),"true"); }
				else if(literal.get(offset+1).equals("[")&&program.get(offset+2).equals("44")&&literal.get(offset+3).equals("]")) 
				{ varset.set(vars.indexOf(literal.get(offset)+"["+literal.get(offset+2)+"]"),"true"); }
				else if(literal.get(offset+1).equals("[")&&literal.get(offset+2).equals("-")&&program.get(offset+3).equals("44")&&literal.get(offset+4).equals("]")) 
				{ 	varset.set(vars.indexOf(literal.get(offset)+"["+literal.get(offset+2)+literal.get(offset+3)+"]"),"true");  }
				return true;
			
			case 111:    				//STORE PROCEDURE PARAMETERS, THEN PROCEDURE ITSELF
				String proctype="(";
				while(offset+j<=end-3){
					num=0;
					while(!literal.get(offset+j).equals(":")) {
						if(program.get(offset+j).equals("43")) {			
							if(vars.contains(literal.get(offset+j))&&(varproc.get(vars.indexOf(literal.get(offset+1))).equals(literal.get(offset-1))))
							{line_of_semantic=Integer.parseInt(line.get(offset+j));return false;}		 //DUPLICATE DECLARATION ERROR : same name in same domain ,main and procedure same name allowed
							else{
								vars.add(literal.get(offset+j));		varproc.add(currentproc);
								varset.add("false");		num++;
							}
						}
						j++;
					}
					j++;
					for(int i=0;i<num;i++) {	vartypes.add(literal.get(offset+j));	 proctype= proctype.concat(literal.get(offset+j)+",");}
					while(!literal.get(offset+j).equals(";")) {j++; }
					j++;
				}
				vars.add(literal.get(offset-1));		vartypes.add(proctype+")");			//ADD PROCEDURE
				varset.add("true");				varproc.add("main");
				return true;
				
			case 110:    				//REMOVE PROCEDURE VARS
				while((k=varproc.indexOf(literal.get(offset+1)))!=-1) {
						vars.remove(k);		vartypes.remove(k);		varproc.remove(k);		varset.remove(k);
				}
				
			default:return true;
		}	
	}
	
	int search(int target, int offset) {
		if(target<0) 	{	debug("false "+target+",0");	return -1;	}
		else if(target<43) 	{		}
		else		{	
			debug(target+","+(offset+1));	
		}

		int i,value=0;
		if(target<46) {							//if Lowest level derivation
			if(!(target==Integer.parseInt(program.get(offset)))) {
				if(target<43) 	{	debug(tokens[target][1]+","+(offset+1));		}
				else { 		debug("false "+target+","+(offset+1));	}
				return -1;
			}
			line_of_last_true = Integer.parseInt(line.get(offset));
			if(target<43) 	{debug("true "+tokens[target][1]+","+(offset+1));}
			else { 		debug("true "+target+","+(offset+1));	}
			return 1;
		}
		
		int numberoftimes=0,length=0,lengthlastbranch=0,cumulative=0;
		switch(syntax[target%100][8]) {			//if regular derivation,switch-case of different types of derivations
			
		    case -1:			//CASE OF A
		    	int lasttruelength=0;
				for(i=0;i<8;i++) {
					if(target<46) {value=target;}
					else {value=syntax[target%100][i];}
					if(value>=0) {
						if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
						lengthlastbranch=search(value,offset+cumulative);
						if(value==133) {lasttruelength=lengthlastbranch;}
						if(lengthlastbranch<0) {
							debug("false "+target+","+(offset+1));
							return -1;
						}
					}
				}
				if(lengthlastbranch>-1) {length=cumulative+lengthlastbranch;}
				debug("true "+target+","+(offset+1));
				
				if(target==118) {if(!storage(target,offset,offset+length-1)) {}}
				else if(target==111) {prevproc="main";  currentproc=literal.get(offset+1);//set current procedure
					if(vars.contains(literal.get(offset+1))&&varproc.get(vars.indexOf(literal.get(offset+1))).equals("main")) {
						line_of_semantic=Integer.parseInt(line.get(offset+1));return -1;} 				//GLOBAL/LOCAL VAR AND PROCEDURE CONFLICT ERROR
					if(literal.get(offset+2).equals(";")) {
						vars.add(currentproc);		vartypes.add("()");
						varset.add("true");				varproc.add("main");
					}
					else {	if(!storage(target,offset+2,offset+length-2)) {}		}
				}
				else if(target==110) { 										//PROCEDURE FINISHED
					if(prevproc=="main") {prevproc="";currentproc="main";}		//revert to main
					if(currentproc=="main") {prevproc="";currentproc="";}
					if(!storage(target,offset,offset+length-1)) {}
				} 
				else if(target==120) {
					if(!exptype(offset+2,offset+length-2).equals("integer")) {
						line_of_semantic=Integer.parseInt(line.get(offset));debug("DEBUG "+exptype(offset+2,offset+length-2));	return -1;	//EXPRESSION NOT INTEGER ERROR
					}
				} 								//if then , while do
				else if((target==137||target==138) && !exptype(offset+1,offset+length-lasttruelength-2).equals("boolean")) {
					checkerdebug("HERE "+literal.get(offset+1));
					line_of_semantic=Integer.parseInt(line.get(offset));	return -1;	//EXPRESSION NOT BOOLEAN ERROR
				}
				if(target==136) {				//if then else
					int j=0;
					while(!literal.get(offset+j).equals("then"))	{	j++;	}
					if(!exptype(offset+1,offset+j-1).equals("boolean")) {
						line_of_semantic=Integer.parseInt(line.get(offset));	return -1;	//EXPRESSION NOT BOOLEAN ERROR
					}
				}
				else if(target==148||target==149) {				//readln,writeln
					int j=1,k=1;
					while(!literal.get(offset+j).equals(";"))	{	
						j++;
						if(literal.get(offset+j).equals(",")||literal.get(offset+j).equals(")")) {
							if(!(exptype(offset+k+1,offset+j-1).equals("integer")||exptype(offset+k+1,offset+j-1).contains("char"))) {
								checkerdebug(literal.get(offset+k+1)+" BULLSHIT");checkerdebug(exptype(offset+k+1,offset+j-1)+" BULLSHIT");line_of_semantic=Integer.parseInt(line.get(offset+k+1));	return -1;	//VARIABLE NOT READ/WRITE-ABLE ERROR
							}
							if(target==148) {
								if(vars.indexOf(literal.get(offset+k+1))==-1) {
									line_of_semantic=Integer.parseInt(line.get(offset+k+1));	return -1;	//UNDECLARED VARIABLE ERROR
								}
								else if(!varproc.get(vars.indexOf(literal.get(offset+k+1))).equals("main") && !varproc.get(vars.indexOf(literal.get(offset+k+1))).equals(currentproc)) {
									line_of_semantic=Integer.parseInt(line.get(offset+k+1));	//VARIABLE OUT OF SCOPE ERROR
									return -1;
								}
							}
							k=j;
						}
					}
				}
				return length;
			
			case -10:			//CASE OF [A]
				numberoftimes=0;
				for(i=0;i<8;i++) {
					if(target<46) {value=target;}
					else {value=syntax[target%100][i];}
					if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
					lengthlastbranch=search(value,offset+cumulative);
					if(lengthlastbranch<0) {  break; }
					numberoftimes++;
				}
				length=cumulative;
				if(numberoftimes==0 || value==-1) {debug("true "+target+","+(offset+1));
						return length;}
				debug("false "+target+","+(offset+1));
				return -1;
				
			case -100:			//CASE OF A|B|C....|Z
				for(i=0;i<8;i++) {
					if(target<46) {value=target;}
					else {value=syntax[target%100][i];}
					if(value>=0) {
						lengthlastbranch=search(value,offset);
						if(lengthlastbranch>-1) {
							debug("true "+target+","+(offset+1));
							if(target==121) {									// PROCEDURE CALL 
								int j=1;checkerdebug("adasds");
								
								if(vars.indexOf(literal.get(offset))==-1) {		//NAME NOT FOUND ERROR
									line_of_semantic=Integer.parseInt(line.get(offset+j));return -1;
								}
								if(!vartypes.get(vars.indexOf(literal.get(offset))).contains("(")) {	//NAME NOT A PROCEDURE ERROR
									return -1;
								}
								String fntype=vartypes.get(vars.indexOf(literal.get(offset)));
								String proctype="(";
								int k=1;
								while(!(literal.get(offset+j).equals(";"))) {										
									if(literal.get(offset+j).equals(",")||literal.get(offset+j).equals(")")) {
										proctype=proctype.concat(exptype(offset+k+1,offset+j-1)+",");
										k=j;
									}
									j++;
								}
								proctype=proctype.concat(")");
								checkerdebug(fntype);
								checkerdebug(proctype);
								if(!proctype.equals(fntype)) 
								{line_of_semantic=Integer.parseInt(line.get(offset+j));return -1;}		 //ARGUMENTS TYPE MISMATCH ERROR
							}
							return lengthlastbranch;
						}
					}
				}
				debug("false "+target+","+(offset+1));
				return -1;
				
			case 0:			//CASE OF {A}
				numberoftimes=0;
				for(i=0;i<Math.max((int)4*(program.size()-program.size()%8),8);i++) {
					if(target<46) {value=target;}
					else {value=syntax[target%100][i%8];}
					if(value>=0) {
						if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
						lengthlastbranch=search(value,offset+cumulative);
						if(lengthlastbranch<0) {  break; }
					}
					numberoftimes++;
				}
				length=cumulative;
				if(numberoftimes%8==0) {debug("true "+target+","+(offset+1));
						return length;}
				debug("false "+target+","+(offset+1));
				return -1;
				
			case 100:			//CASE OF A{A}
				numberoftimes=0;
				for(i=0;i<Math.max((int)4*(program.size()-program.size()%8),8);i++) {
					if(target<46) {value=target;}
					else {value=syntax[target%100][i%8];}
					if(value>=0) {
						if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
						if(i>7) {lengthlastbranch=search(value,offset+cumulative);}
						else{lengthlastbranch=search(value,offset+cumulative);}
						if(lengthlastbranch<0) {  break; }
					}
					numberoftimes++;
				}
				length=cumulative;
				if(numberoftimes%8==0&&numberoftimes>0) {
					if(target==103) {
						if(!storage(target,offset,offset+length-1)) {}
					}
					debug("true "+target+","+(offset+1));
					return length;
				}
				debug("false "+target+","+(offset+1));
				return -1;
				
			case 99:			//CASE OF A{;A}
				numberoftimes=0;
				for(i=0;i<Math.max((int)4*(program.size()-program.size()%9),9);i++) {
					if(target<46) {value=target;}
					else {value=syntax[target%100][i%9];}
					if(i%9==8&&value>=0) {
						if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
						lengthlastbranch=search(37,offset+cumulative);
						if(lengthlastbranch<0) {  break; }
					}
					else if(value>=0) {
						if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
						if(i>8) {lengthlastbranch=search(value,offset+cumulative);}
						else{lengthlastbranch=search(value,offset+cumulative);}
						if(lengthlastbranch<0) {  break; }
					}
					numberoftimes++;
				}
				length=cumulative;
				if(numberoftimes%9==8 ) {debug("true "+target+","+(offset+1));
						return length;}
				debug("false "+target+","+(offset+1));
				return -1;
				
			case 98:			//CASE OF A{,A}
				numberoftimes=0;
				for(i=0;i<Math.max((int)4*(program.size()-program.size()%9),9);i++) {
					if(target<46) {value=target;}
					else {value=syntax[target%100][i%9];}
					if(i%9==8&&value>=0) {
						if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
						lengthlastbranch=search(41,offset+cumulative);
						if(lengthlastbranch<0) {  break; }
					}
					else if(value>=0) {
						if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
						if(i>8) {lengthlastbranch=search(value,offset+cumulative);}
						else{lengthlastbranch=search(value,offset+cumulative);}
						if(lengthlastbranch<0) {  break; }
					}
					numberoftimes++;
				}
				
				length=cumulative;
				if(numberoftimes%9==8 ) {debug("true "+target+","+(offset+1));
						return length;}
				debug("false "+target+","+(offset+1));
				return -1;
			
			default: debug("false "+target+","+(offset+1));
				return -1;
		}
	}
}

