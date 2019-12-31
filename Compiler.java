package enshud.s4.compiler;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Arrays;
import enshud.casl.CaslSimulator;

public class Compiler {
	private static final int DEBUG = 0;
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
		  { 138, 137, 117, -1, -1, -1, -1, -1,		-100 },  //new
		  { 118, 121, 130, 133, -1, -1, -1, -1,		-100 },
		  { 119, 40, 123, -1, -1, -1, -1, -1,		-1 },
		  { 120, 43, -1, -1, -1, -1, -1, -1,		-100 },
		  //120
		  { 43, 35, 123, 36, -1, -1, -1, -1,	-1 },
		  { 139, 43, -1, -1, -1, -1, -1, -1,		-100 },
		  { 123, -1, -1, -1, -1, -1, -1, -1,		98 },
		  { 124, 140, -1, -1, -1, -1, -1, -1,		-1 },
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
		  {  7, 133,-1, -1, -1, -1, -1, -1,		-1 },		//new
		  { 10, 123, 19, 133, -1, -1, -1, -1,		-1 },
		  { 22, 123, 6, 133, -1, -1, -1, -1,		-1 },
		  
		  { 43, 33, 122, 34, -1, -1, -1, -1,		-1 },
		  { 127, 124, -1, -1, -1, -1, -1, -1,		-10 },
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
	
	
	public static void main(final String[] args) {
		// Compilerを実行してcasを生成する
		//new Compiler().run("data/ts/normal15.ts", "tmp/out.cas");
		new Compiler().run("tmp/temp.ts", "tmp/out.cas");
		//new Compiler().run("data/ts/synerr08.ts", "tmp/out.cas");

		// 上記casを，CASLアセンブラ & COMETシミュレータで実行する
		CaslSimulator.run("tmp/out.cas", "tmp/out.ans");
	}
	
	ArrayList<String> program = new ArrayList<String>();
	ArrayList<String> line = new ArrayList<String>();
	ArrayList<String> literal = new ArrayList<String>();
	ArrayList<String> vars = new ArrayList<String>();
	ArrayList<String> vartypes = new ArrayList<String>();
	ArrayList<String> varproc = new ArrayList<String>();
	ArrayList<String> strings = new ArrayList<String>();
	ArrayList<String> assignablevars = new ArrayList<String>();
	ArrayList<String> assignablevarproc = new ArrayList<String>();
	ArrayList<String> procs = new ArrayList<String>();
	String currentproc = "main";
	String prevproc = "";
	public int line_of_last_true = 1;
	public int line_of_semantic = 0;
	int lastline = 0;
	int tagcount=0,loopcount=0,elsecount=0,endifcount=0,endlpcount=0,proccount=0;
	ArrayList<Integer> procparasize= new ArrayList<Integer>();
	ArrayList<Integer> elsecountstack= new ArrayList<Integer>();
	ArrayList<Integer> endifcountstack= new ArrayList<Integer>();
	ArrayList<Integer> loopcountstack = new ArrayList<Integer>();
	ArrayList<Integer> endlpcountstack = new ArrayList<Integer>();
	/**
	 * TODO
	 * 
	 * 開発対象となるCompiler実行メソッド．
	 * 以下の仕様を満たすこと．
	 * 
	 * 仕様:
	 * 第一引数で指定されたtsファイルを読み込み，CASL IIプログラムにコンパイルする．
	 * コンパイル結果のCASL IIプログラムは第二引数で指定されたcasファイルに書き出すこと．
	 * 構文的もしくは意味的なエラーを発見した場合は標準エラーにエラーメッセージを出力すること．
	 * （エラーメッセージの内容はChecker.run()の出力に準じるものとする．）
	 * 入力ファイルが見つからない場合は標準エラーに"File not found"と出力して終了すること．
	 * 
	 * @param inputFileName 入力tsファイル名
	 * @param outputFileName 出力casファイル名
	 */
	String s =";;\r\n;;\r\n;;\r\n"+ "CASL	START	BEGIN	; \r\n" + 
			"BEGIN	LAD	GR6, 0	; \r\n" + 
			"	LAD	GR7, LIBBUF	; \r\n";
	
	public void run(final String inputFileName, final String outputFileName) {
		int numberofvars=0;

		try  {
			Checker check= new Checker();
			check.run(inputFileName);
			
			program=check.program;
			if(program.size()==0) {return;}
			line=check.line;
			literal=check.literal;
			vars=check.allvars;
			vartypes=check.allvartypes;
			varproc=check.allvarproc;
			
			for(int i=0;i<vars.size();i++) {
				String temp=vartypes.get(i);
				if(temp.charAt(temp.length()-1)!=']'&&temp.charAt(0)!='(') {numberofvars++;assignablevars.add(vars.get(i));assignablevarproc.add(varproc.get(i));}
				else if(temp.charAt(0)=='(') {
					procparasize.add(temp.length() - temp.replace(",", "").length());
					procs.add(vars.get(i));
				}
			}
			for(int i=0;i<program.size();i++) {
				if(program.get(i).equals("45")) {if(literal.get(i).length()>3) {strings.add(literal.get(i));}}
			}
			search(100,0);
			s=s.concat("	RET		;\r\n");
			s=s.replaceAll("\r\n	;procs start([\\s\\S]*)	;procs end([\\s\\S]*)","$2	;procs start$1	;procs end\r\n");//shift procs to after main program
			s=s.concat("VAR	DS	"+String.valueOf(numberofvars)+"	;\r\n");
			for(int i=0;i<strings.size();i++) {
				s=s.concat("CHAR"+i+"	DC	"+strings.get(i)+"	; \r\n");
			}
			s=s.concat("LIBBUF	DS	256	; \r\n"+"	END		; \r\n");
			String libfile = new Scanner(new File("data/cas/lib.cas")).useDelimiter("\\Z").next();
			s=s.concat(libfile);
		    try (PrintWriter out = new PrintWriter(outputFileName)) {
		           out.println(s);
		    }
		}
    	catch (FileNotFoundException b) {
    		
    	}
	}
	
	String exptype(int offset,int end) {//System.out.println(line.get(offset)+" | "+(offset+1)+" , "+(end+1));
		if(end-offset<0) {}
		else if(end-offset<1) {//System.out.println(line.get(offset)+" | "+(offset+1)+" , "+(end+1)+" , "+literal.get(offset));
			if(program.get(offset).equals("44")) {
				if(Integer.parseInt(literal.get(offset))<=32768&&literal.get(offset-1).equals("-")) {
					s=s.concat("	PUSH	"+literal.get(offset)+"	;\r\n");
					if(literal.get(offset-2).equals(":=")||literal.get(offset-2).equals("(")) {
						s=s.concat("	POP	GR2	; assign	minus\r\n" + "	LD	GR1, =0	; assign	minus\r\n" + 
							"	SUBA	GR1, GR2	; assign	minus\r\n" + "	PUSH	0, GR1	;\r\n");
					}
					return "integer";}  
				else if(Integer.parseInt(literal.get(offset))<=32767&&!literal.get(offset-1).equals("-")) {
					s=s.concat("	PUSH	"+literal.get(offset)+"	;\r\n");return "integer";}  
			}
			else if(program.get(offset).equals("45")) {
				int length=literal.get(offset).length()-2;
				if(length>1) {
					int stringnumber = strings.indexOf(literal.get(offset));
					if(stringnumber>-1) {
						s=s.concat("	LD	GR1, ="+length+"	;\r\n	PUSH	0, GR1	;\r\n	LAD	GR2, CHAR"+stringnumber+"	;\r\n	PUSH	0, GR2	; \r\n	POP	GR2	; \r\n	POP	GR1	; \r\n");
					}
					return "char [1.."+length+"]";
				}	
				else {
					s=s.concat("	LD	GR1, ="+literal.get(offset)+"	;\r\n	PUSH	0, GR1	;\r\n");
					return "char";
				}
			}
			else if(literal.get(offset).equals("true")) {	s=s.concat("	PUSH	#0000	;\r\n");return "boolean";	}
			else if(literal.get(offset).equals("false")) {	s=s.concat("	PUSH	#FFFF	;\r\n");return "boolean";	}
			else if((currentproc.equals("main")&&varproc.get(vars.indexOf(literal.get(offset))).equals("main"))  //retreive main procedure variables in main
					||varproc.get(vars.indexOf(literal.get(offset))).equals(currentproc)	//retreive subprocedure variables
					||varproc.get(vars.indexOf(literal.get(offset))).equals("main")			//retreive main procedure variables in subproc
					||(!currentproc.equals("main")&&!varproc.get(vars.indexOf(literal.get(offset))).equals(currentproc)))		//case of name conflict	
			{		
				int address=assignablevars.indexOf(literal.get(offset));
				if(!currentproc.equals("main")&&!varproc.get(vars.indexOf(literal.get(offset))).equals(currentproc)) {  
					//subprocedure name conflict resolution
					for(int i=0;i<assignablevars.size();i++) {
						if(assignablevarproc.get(i).equals(currentproc)&&assignablevars.get(i).equals(literal.get(offset))) {address=i;}
					}
				}
				if(!literal.get(offset+1).equals("[")) {
					s=s.concat("	LD	GR2, ="+address+"	; var	"+assignablevars.get(address)+"\r\n" + "	LD	GR1, VAR, GR2	; var	"+assignablevars.get(address)+"\r\n" + "	PUSH	0, GR1	;\r\n");
				}
				else {
					String arrtype=vartypes.get(vars.indexOf(literal.get(offset)));
					int z=arrtype.indexOf('['),z2=arrtype.indexOf('.'),z3=arrtype.indexOf(']');
					address=assignablevars.indexOf(literal.get(offset)+"["+Integer.parseInt(arrtype.substring(z+1,z2))+"]");
					if(!currentproc.equals("main")&&!varproc.get(vars.indexOf(literal.get(offset))).equals(currentproc)) {  
						//subprocedure name conflict resolution(array element)
						for(int i=0;i<assignablevars.size();i++) {
							if(assignablevarproc.get(i).equals(currentproc)&&assignablevars.get(i).equals(literal.get(offset)+"["+Integer.parseInt(arrtype.substring(z+1,z2))+"]")) {address=i;}
						}
					}
					int k=0;
					while(!literal.get(end-k).equals("]"))	{	k++;}
					exptype(offset+2,end-k-1);
					s=s.concat("	POP	GR2	;	array element\r\n	ADDA	GR2, ="+(address-1)+"	;	array element\r\n	LD	GR1, VAR, GR2	; array element\r\n	PUSH	0, GR1	;	array element\r\n");
				}
				return vartypes.get(vars.indexOf(literal.get(offset)));
			}   
		}
		else {
			int hassign=0;
			int i=0,turnoff=0;
			String temp;    //SHIKI -> TANJUNSHIKI KANKEI TANJUNSHIKI
			while(offset+i!=end&&(turnoff!=0||(!literal.get(offset+i).equals("=")&&!literal.get(offset+i).equals("<>")&&!literal.get(offset+i).equals("<")&&!literal.get(offset+i).equals(">")&&!literal.get(offset+i).equals("<=")&&!literal.get(offset+i).equals(">="))))
			{	
				if(literal.get(offset+i).equals("[")){	turnoff+=10;		}
				else if(literal.get(offset+i).equals("(")) {	turnoff-=1;		}
				else if(literal.get(offset+i).equals(")")&&turnoff<0) {	turnoff+=1;		}
				else if(literal.get(offset+i).equals("]")&&turnoff>0) {	turnoff-=10;		}
				i++;	
			}
			if(offset+i!=end) {
				temp=exptype(offset,offset+i-1);
				if(temp.equals(exptype(offset+i+1,end))) {
					s=s.concat("	POP	GR2	; \r\n	POP	GR1	; \r\n");
					s=s.concat("	CPA	GR1, GR2	; \r\n");
					if(literal.get(offset+i).equals("<")) {
						s=s.concat("	JMI	TRUE"+(tagcount++)+"	; \r\n"+"	LD	GR1, =#FFFF	; \r\n"+"	JUMP	END"+(tagcount++)+"	; \r\n"+"TRUE"+(tagcount-2)+"	LD	GR1, =#0000	;\r\n"+"END"+(tagcount-1)+"	PUSH	0, GR1	; \r\n");
					}
					if(literal.get(offset+i).equals(">")) {
						s=s.concat("	JPL	TRUE"+(tagcount++)+"	; \r\n"+"	LD	GR1, =#FFFF	; \r\n"+"	JUMP	END"+(tagcount++)+"	; \r\n"+"TRUE"+(tagcount-2)+"	LD	GR1, =#0000	;\r\n"+"END"+(tagcount-1)+"	PUSH	0, GR1	; \r\n");
					}
					if(literal.get(offset+i).equals(">=")) {
						s=s.concat("	JMI	FALSE"+(tagcount++)+"	; \r\n"+"	LD	GR1, =#0000	; \r\n"+"	JUMP	END"+(tagcount++)+"	; \r\n"+"FALSE"+(tagcount-2)+"	LD	GR1, =#FFFF	;\r\n"+"END"+(tagcount-1)+"	PUSH	0, GR1	; \r\n");
					}
					if(literal.get(offset+i).equals("<=")) {
						s=s.concat("	JPL	FALSE"+(tagcount++)+"	; \r\n"+"	LD	GR1, =#0000	; \r\n"+"	JUMP	END"+(tagcount++)+"	; \r\n"+"FALSE"+(tagcount-2)+"	LD	GR1, =#FFFF	;\r\n"+"END"+(tagcount-1)+"	PUSH	0, GR1	; \r\n");
					}
					if(literal.get(offset+i).equals("=")) {
						s=s.concat("	JZE	TRUE"+(tagcount++)+"	; \r\n"+"	LD	GR1, =#FFFF	; \r\n"+"	JUMP	END"+(tagcount++)+"	; \r\n"+"TRUE"+(tagcount-2)+"	LD	GR1, =#0000	;\r\n"+"END"+(tagcount-1)+"	PUSH	0, GR1	; \r\n");
					}
					if(literal.get(offset+i).equals("<>")) {
						s=s.concat("	JZE	FALSE"+(tagcount++)+"	; \r\n"+"	LD	GR1, =#0000	; \r\n"+"	JUMP	END"+(tagcount++)+"	; \r\n"+"FALSE"+(tagcount-2)+"	LD	GR1, =#FFFF	;\r\n"+"END"+(tagcount-1)+"	PUSH	0, GR1	; \r\n");
					}
					return "boolean";
				}
			}
			else {			//TANJUNSHIKI -> KOU
				i=0;turnoff=0;
				if(literal.get(offset).equals("+")||literal.get(offset).equals("-")) {hassign=1;}
				while((end-i!=offset+hassign)&&(turnoff!=0||(!literal.get(end-i).equals("+")&&!literal.get(end-i).equals("-")&&!literal.get(end-i).equals("or"))))
				{	
					if(literal.get(end-i).equals("]")){	turnoff+=10;		}
					else if(literal.get(end-i).equals(")")) {	turnoff-=1;		}
					else if(literal.get(end-i).equals("(")&&turnoff<0) {	turnoff+=1;		}
					else if(literal.get(end-i).equals("[")&&turnoff>0) {	turnoff-=10;		}
					i++;	
				}
				if(end-i!=offset+hassign&&literal.get(end-i).equals("or")) {
					temp=exptype(end-i+1,end);
					if(temp.equals(exptype(offset,end-i-1))&&temp.equals("boolean")) {
						s=s.concat("	POP	GR1	; \r\n	POP	GR2	; \r\n");
						s=s.concat("	OR	GR1,GR2	; \r\n	PUSH	0, GR1	; \r\n");
						return "boolean";
					}
				}
				else if(end-i!=offset+hassign) {
					temp=exptype(end-i+1,end);
					if(temp.equals(exptype(offset,end-i-1))&&temp.equals("integer")) {
						s=s.concat("	POP	GR1	; \r\n	POP	GR2	; \r\n");
						if(literal.get(end-i).equals("+")) {s=s.concat("	ADDA	GR1, GR2	; \r\n");}
						if(literal.get(end-i).equals("-")) {s=s.concat("	SUBA	GR1, GR2	; \r\n");}
						s=s.concat("	PUSH	0, GR1	; \r\n");
						return "integer";
					}
				}
				else {			//KOU -> INSHI
					i=0;turnoff=0;
					while(end-i!=offset+hassign&&(turnoff!=0||(!literal.get(end-i).equals("/")&&!literal.get(end-i).equals("div")&&!literal.get(end-i).equals("*")&&!literal.get(end-i).equals("and")&&!literal.get(end-i).equals("mod"))))
					{	
						if(literal.get(end-i).equals("]")){	turnoff+=10;		}
						else if(literal.get(end-i).equals(")")) {	turnoff-=1;		}
						else if(literal.get(end-i).equals("(")&&turnoff<0) {	turnoff+=1;		}
						else if(literal.get(end-i).equals("[")&&turnoff>0) {	turnoff-=10;		}
						i++;	
					}
					if(end-i!=offset+hassign&&literal.get(offset+i).equals("and")) {
						temp=exptype(end-i+1,end);
						if(temp.equals(exptype(offset,end-i-1))&&temp.equals("boolean")) {
							s=s.concat("	POP	GR1	; \r\n	POP	GR2	; \r\n");
							s=s.concat("	AND	GR1,GR2	; \r\n	PUSH	0, GR1	; \r\n");
							return "boolean";
						}
					}
					else if(end-i!=offset+hassign) {
						temp=exptype(end-i+1,end);
						if(temp.equals(exptype(offset,end-i-1))&&temp.equals("integer")) {
							s=s.concat("	POP	GR1	; \r\n	POP	GR2	; \r\n");
							if(literal.get(end-i).equals("div")||literal.get(end-i).equals("/")) {s=s.concat("	CALL	DIV	; \r\n	PUSH	0, GR2	; \r\n");}
							if(literal.get(end-i).equals("*")) {s=s.concat("	CALL	MULT	; \r\n	PUSH	0, GR2	; \r\n");}
							if(literal.get(end-i).equals("mod")) {s=s.concat("	CALL	DIV	; \r\n	PUSH	0, GR1	; \r\n");}
							return "integer";
						}
					}
					else {			//INSHI -> HENSUU|(SHIKI)|NOT INSHI
						if(literal.get(offset+hassign).equals("not")) {
							if(exptype(offset+hassign+1,end).equals("boolean")) {
								s=s.concat("	POP	GR1	; \r\n	XOR	GR1,=#FFFF	; \r\n	PUSH	0, GR1	; \r\n");
								return "boolean";
							}
						}
						else if(literal.get(offset+hassign).equals("(")) {
							String str= exptype(offset+1+hassign,end-1);
							if(hassign>0) {//System.out.println("HERE   "+(offset+1)+","+(end+1));
								s=s.concat("	POP	GR2	; var	minus\r\n" + "	LD	GR1, =0	; bracket	minus\r\n" + 
										"	SUBA	GR1, GR2	; bracket	minus\r\n" + "	PUSH	0, GR1	;\r\n");
							}
							return str;
						}
						else {      		//HENSUU/TEISUU
							if(literal.get(offset+1+hassign).equals("[")) {
								int j=hassign;
								while(!literal.get(offset+j).equals("]"))
								{	j++;	}
								int address=assignablevars.indexOf(literal.get(offset+hassign));
								String arrtype=vartypes.get(vars.indexOf(literal.get(offset+hassign)));
								int z=arrtype.indexOf('['),z2=arrtype.indexOf('.'),z3=arrtype.indexOf(']');
								exptype(offset+hassign+2,offset+j-1);
								address=assignablevars.indexOf(literal.get(offset+hassign)+"["+Integer.parseInt(arrtype.substring(z+1,z2))+"]");
								s=s.concat("	POP	GR2	;	array element\r\n	ADDA	GR2, ="+(address-1)+"	;	array element\r\n	LD	GR1, VAR, GR2	;	array element\r\n	PUSH	0, GR1	;	array element\r\n");
											//ARRAY ELEMENT HENSUU/TEISUU	
								StringTokenizer tokens = new StringTokenizer(vartypes.get(vars.indexOf(literal.get(offset+hassign))));
								if(hassign>0&&literal.get(offset).equals("-")) {
									s=s.concat("	POP	GR2	; array	minus\r\n" + "	LD	GR1, =0	; array	minus\r\n" + 
											"	SUBA	GR1, GR2	; array	minus\r\n" + "	PUSH	0, GR1	;\r\n");
								}
								return tokens.nextToken();
							}
							else{
								String str= exptype(offset+hassign,offset+hassign);
								if(hassign>0&&program.get(offset+1).equals("43")&&literal.get(offset).equals("-")) {
									s=s.concat("	POP	GR2	; var	minus\r\n" + "	LD	GR1, =0	; var	minus\r\n" + 
											"	SUBA	GR1, GR2	; var	minus\r\n" + "	PUSH	0, GR1	;\r\n");
								}
								return str;
							}     //NON ARRAY ELEMENT HENSUU/TEISUU
						}
					}
				}
			}
		}
		return "ERROR";
	}

	
	int search(int target, int offset) {
		if(Integer.parseInt(line.get(offset))>=lastline&&(target==116||target==118)&&!literal.get(offset).equals("end")) {s=s.concat(";L"+line.get(offset)+"	"+literal.get(offset)+"\r\n");}
		lastline=Integer.parseInt(line.get(offset));
		if(target<0) 	{		return -1;	}
		else if(target<43) 	{		}
		else		{	
			//System.out.println(target+","+(offset+1));	
			}
		int i,value=0;
		if(target<46) {							//if Lowest level derivation
			if(!(target==Integer.parseInt(program.get(offset)))) {
				return -1;
			}
			line_of_last_true = Integer.parseInt(line.get(offset));
			if(target<43) 	{}
			return 1;
		}
		
		int numberoftimes=0,length=0,lengthlastbranch=0,cumulative=0;
		switch(syntax[target%100][8]) {			//if regular derivation,switch-case of different types of derivations
			
		    case -1:			//CASE OF A
		    	//if then 
				if((target==137)&&literal.get(offset).equals("if")) {	int j=0;
					while(!literal.get(offset+j).equals("then"))	{	j++;	}
					exptype(offset+1,offset+j-1);
					elsecountstack.add(elsecount);
					s=s.concat("	POP	GR1	; if\r\n" + "	CPL	GR1, =#FFFF	; if\r\n" + "	JZE	ELSE"+(elsecount++)+"	; if\r\n");
				}
				else if((target==138)&&literal.get(offset).equals("while")) {	int j=0;
					while(!literal.get(offset+j).equals("do"))	{	j++;	}
					exptype(offset+1,offset+j-1);
					endlpcountstack.add(endlpcount);
					s=s.concat("	POP	GR1	; while\r\n" + "	CPL	GR1, =#FFFF	; while\r\n" + "	JZE	ENDLP"+(endlpcount++)+"	; while\r\n");
				}
				else if(target==118&&literal.get(offset+1).equals("[")) {	int j=0,k=0;
					while(!literal.get(offset+j).equals("]"))	{	j++;	}
					while(!literal.get(offset+k).equals(";"))	{	k++;	}
				}	
				else if(target==120&&literal.get(offset+1).equals("[")) {	
					int j=0;
					while(!literal.get(offset+j).equals("]"))	{	j++;	}
					
				}
				int lasttruelength=0;
				for(i=0;i<8;i++) {
					value=syntax[target%100][i];
					if(value>=0) {
						if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
						if(value==109){s=s.concat("	;procs start\r\n");}
						lengthlastbranch=search(value,offset+cumulative);
						if(lengthlastbranch>-1&&line.get(offset).equals("68")){
							//System.out.println(value+","+lengthlastbranch+","+(offset+cumulative+lengthlastbranch+1));
						}
						if(value==109){s=s.concat("	;procs end\r\n");}
						if(value==133) {lasttruelength=lengthlastbranch;}
						if(lengthlastbranch<0) {return -1;}
					}
				}
				if(lengthlastbranch>-1) {length=cumulative+lengthlastbranch;}
				
				if(target==111) {prevproc="main";  currentproc=literal.get(offset+1);}//set current procedure
				else if(target==110) { 										//PROCEDURE FINISHED
					if(prevproc.equals("main")) {prevproc="";currentproc="main";}		//revert to main
					else if(currentproc.equals("main")) {prevproc="";currentproc="";}
				} 
				else if(target==118) {	
					int address=0;
					if(literal.get(offset+1).equals(":=")) {
						address=assignablevars.indexOf(literal.get(offset));
						if(!currentproc.equals("main")&&!varproc.get(vars.indexOf(literal.get(offset))).equals(currentproc)) {	//subprocedure name conflict resolution - assign
							for(int k=0;k<assignablevars.size();k++) {
								if(assignablevarproc.get(k).equals(currentproc)&&assignablevars.get(k).equals(literal.get(offset))) {address=k;}
							}
						}
						exptype(offset+2,offset+length-1);	
						s=s.concat("	LD	GR2, ="+address+"	;\r\n	POP	GR1	;\r\n	ST	GR1, VAR, GR2	;\r\n");
					} 
					else {
						String arrtype=vartypes.get(vars.indexOf(literal.get(offset)));
						int z=arrtype.indexOf('['),z2=arrtype.indexOf('.'),z3=arrtype.indexOf(']');
						address=assignablevars.indexOf(literal.get(offset)+"["+Integer.parseInt(arrtype.substring(z+1,z2))+"]");
						if(!currentproc.equals("main")&&!varproc.get(vars.indexOf(literal.get(offset))).equals(currentproc)) {  //subprocedure name conflict resolution - assign(array element)
							for(int k=0;k<assignablevars.size();k++) {
								if(assignablevarproc.get(k).equals(currentproc)&&assignablevars.get(k).equals(literal.get(offset)+"["+Integer.parseInt(arrtype.substring(z+1,z2))+"]")) {address=i;}
							}
						}
						int k=0;
						while(!literal.get(offset+k).equals(":="))	{	k++;}
						exptype(offset+2,offset+k-2);
						exptype(offset+k+1,offset+length-1);
						s=s.concat("	POP	GR1	;array element\r\n	POP	GR2	;array element\r\n	ADDA	GR2, ="+(address-1)+"	;array element\r\n	ST	GR1, VAR, GR2	;array element\r\n");
					}
				}
				else if(target==137) {
					if(literal.get(offset+length).equals("else")) {		//if then else
						endifcountstack.add(endifcount);
						s=s.concat("	JUMP	ENDIF"+(endifcount++)+"	; if else \r\n");
						s=s.concat("ELSE"+elsecountstack.get(elsecountstack.size()-1)+"	NOP	; if else \r\n");
						elsecountstack.remove(elsecountstack.size()-1);
						length+=search(136,offset+length);
						s=s.concat("ENDIF"+endifcountstack.get(endifcountstack.size()-1)+"	NOP	; end if else \r\n");
						endifcountstack.remove(endifcountstack.size()-1);
					} 
					else{s=s.concat("ELSE"+elsecountstack.get(elsecountstack.size()-1)+"	NOP	; if\r\n");
					elsecountstack.remove(elsecountstack.size()-1);}	//if then
				}
				else if(target==138) {
					s=s.concat("	JUMP	LOOP"+loopcountstack.get(loopcountstack.size()-1)+"	; while\r\nENDLP"+endlpcountstack.get(endlpcountstack.size()-1)+"	NOP	; while\r\n");	//while loop
					loopcountstack.remove(loopcountstack.size()-1);
					endlpcountstack.remove(endlpcountstack.size()-1);
				}
				else if((target==148&&literal.get(offset).equals("readln"))||(target==149&&literal.get(offset).equals("writeln"))) {		//readln,writeln
					int j=1,k=1;
					while(!literal.get(offset+j).equals(";"))	{	
						j++;
						if(literal.get(offset+j).equals(",")||literal.get(offset+j).equals(")")) {
							String type=exptype(offset+k+1,offset+j-1);		
							if(target==149&&type.equals("char")) {s=s.concat("	POP	GR2	;\r\n");s=s.concat("	CALL	WRTCH	;\r\n");}
							else if(target==149&&type.contains("char")) {s=s.concat("	CALL	WRTSTR	;\r\n");}
							else if(target==149&&type.equals("integer")) {s=s.concat("	POP	GR2	;\r\n");s=s.concat("	CALL	WRTINT	;\r\n");}
							else {}
							k=j;
						}
					}
					if(target==149) {s=s.concat("	CALL	WRTLN	;\r\n");}
				}
				return length;
			
			case -10:			//CASE OF [A]
				numberoftimes=0;
				for(i=0;i<8;i++) {
					value=syntax[target%100][i];
					if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
					lengthlastbranch=search(value,offset+cumulative);
					if(lengthlastbranch<0) {  break; }
					numberoftimes++;
				}
				length=cumulative;
				if(numberoftimes==0 || value==-1) {return length;}
				return -1;
				
			case -100:			//CASE OF A|B|C....|Z
				if(target==116) {	
					if(literal.get(offset).equals("while")) {
						loopcountstack.add(loopcount);
						s=s.concat("LOOP"+(loopcount++)+"	NOP		;	while\r\n");
					}
				}
				for(i=0;i<8;i++) {
					value=syntax[target%100][i];
					if(value>=0) {
						lengthlastbranch=search(value,offset);
						if(lengthlastbranch>-1) {
							if(target==121) {									// PROCEDURE CALL 
								int j=1,k=1;
								while(!(literal.get(offset+j).equals(";"))) {										
									if(literal.get(offset+j).equals(",")||literal.get(offset+j).equals(")")) {
										exptype(offset+k+1,offset+j-1);
										k=j;
									}
									j++;
								}
								s=s.concat("	CALL	PROC"+procs.indexOf(literal.get(offset))+"	;\r\n");	
							}
							return lengthlastbranch;
						}
					}
				}
				return -1;
				
			case 0:			//CASE OF {A}
				numberoftimes=0;
				for(i=0;i<Math.max((int)4*(program.size()-program.size()%8),8);i++) {
					value=syntax[target%100][i%8];
					if(value>=0) {
						if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
						if(value==110&&literal.get(offset+cumulative).equals("procedure")){
							s=s.concat("PROC"+(proccount++)+"	NOP	;\r\n");
							
							int parasize = procparasize.get(procs.indexOf(literal.get(offset+cumulative+1)));
							if(parasize==0) {s=s.concat("	LD	GR1, GR8	; proc	local-var\r\n");s=s.concat("	ADDA	GR1, =0	;\r\n");}
													
							for(int j=0;j<=parasize-1;j++) {
								s=s.concat("	LD	GR1, GR8	; proc	local-var\r\n");
								s=s.concat("	ADDA	GR1, ="+(parasize-j)+"	; proc	local-var\r\n");
								
								int address=assignablevars.indexOf(literal.get(offset+cumulative+3+2*j));
								//parameter name conflict resolution
								for(int k=address;k<assignablevars.size();k++) {
									if(assignablevarproc.get(k).equals(literal.get(offset+cumulative+1))&&assignablevars.get(k).equals(literal.get(offset+cumulative+3+2*j))) {
										address=k;break;
									}
								}
								
								s=s.concat("	LD	GR2, 0, GR1	; proc	fparam	("+assignablevars.get(address)+")\r\n	LD	GR3, ="+address+"	;\r\n	ST	GR2, VAR, GR3	;\r\n	SUBA	GR1, =1	;\r\n");
							}
							s=s.concat("	LD	GR1, 0, GR8	; proc	fparam\r\n	ADDA	GR8, ="+parasize+"	; proc	fparam\r\n	ST	GR1, 0, GR8	; proc	fparam\r\n");
						}
						lengthlastbranch=search(value,offset+cumulative);
						
						if(lengthlastbranch<0) {  break; }
						if(value==110) {s=s.concat("	RET		;\r\n");}
					}
					numberoftimes++;
				}
				length=cumulative;
				if(numberoftimes==0||numberoftimes%8==0) {return length;}
				return -1;
				
			case 100:			//CASE OF A{A}
				numberoftimes=0;
				for(i=0;i<Math.max((int)4*(program.size()-program.size()%8),8);i++) {
					value=syntax[target%100][i%8];
					if(value>=0) {
						if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
						if(i>7) {lengthlastbranch=search(value,offset+cumulative);}
						else{lengthlastbranch=search(value,offset+cumulative);}
						if(lengthlastbranch<0) {  break; }
					}
					numberoftimes++;
				}
				length=cumulative;
				if(numberoftimes%8==0&&numberoftimes>0) {return length;}
				return -1;
				
			case 99:			//CASE OF A{;A}
				numberoftimes=0;
				for(i=0;i<Math.max((int)4*(program.size()-program.size()%9),9);i++) {
					value=syntax[target%100][i%9];
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
				if(numberoftimes%9==8 ) {return length;}
				return -1;
				
			case 98:			//CASE OF A{,A}
				numberoftimes=0;
				for(i=0;i<Math.max((int)4*(program.size()-program.size()%9),9);i++) {
					value=syntax[target%100][i%9];
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
				if(numberoftimes%9==8 ) {return length;}
				return -1;
			
			default: return -1;
		}
	}
}
