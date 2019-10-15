package enshud.s2.parser;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Parser {
	private static final boolean DEBUG = false;
	public int line_of_last_true = 1;
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
	public void debug(final String message) {
		if(DEBUG) {System.out.println(message);}
	}
	
	public static void main(final String[] args) {
		// normalの確認
		//new Parser().run("data/ts/normal01.ts");
		//new Parser().run("data/ts/normal15.ts");

		// synerrの確認
		new Parser().run("data/ts/synerr02.ts");
		//new Parser().run("data/ts/synerr02.ts");
	}

	/**
	 * TODO
	 * 
	 * 開発対象となるParser実行メソッド．
	 * 以下の仕様を満たすこと．
	 * 
	 * 仕様:
	 * 第一引数で指定されたtsファイルを読み込み，構文解析を行う．
	 * 構文が正しい場合は標準出力に"OK"を，正しくない場合は"Syntax error: line"という文字列とともに，
	 * 最初のエラーを見つけた行の番号を標準エラーに出力すること （例: "Syntax error: line 1"）．
	 * 入力ファイル内に複数のエラーが含まれる場合は，最初に見つけたエラーのみを出力すること．
	 * 入力ファイルが見つからない場合は標準エラーに"File not found"と出力して終了すること．
	 * 
	 * @param inputFileName 入力tsファイル名
	 */
	ArrayList<String> program = new ArrayList<String>();
	ArrayList<String> line = new ArrayList<String>();
	
	public void run(final String inputFileName) 
	{
		
		try  {
			String content = new Scanner(new File(inputFileName)).useDelimiter("\\Z").next();
			StringTokenizer lines = new StringTokenizer(content, "\n");
			while (lines.hasMoreTokens()) {
				StringTokenizer words = new StringTokenizer(lines.nextToken(), "\t") ;		
				String next=words.nextToken(); 
				next=words.nextToken(); 
				next=words.nextToken();  
				program.add(next);
				next=words.nextToken();  
				line.add(next);
			}	

			if(search(100,0)>=0) {System.out.println("OK");}
			else {System.err.println("Syntax error: line "+line_of_last_true);}
        }
    	catch (FileNotFoundException b) {System.err.println("File not found");}
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
		
		int numberoftimes=0,length=0,lengthlastbranch=0,cumulative=0,flag=0;
		switch(syntax[target%100][8]) {			//if regular derivation,switch-case of different types of derivations
			
		    case -1:			//CASE OF A
				for(i=0;i<8;i++) {
					if(target<46) {value=target;}
					else {value=syntax[target%100][i];}
					if(value>=0) {
						if(lengthlastbranch>-1) {cumulative+=lengthlastbranch;}
						lengthlastbranch=search(value,offset+cumulative);
						if(lengthlastbranch<0) {
							debug("false "+target+","+(offset+1));
							return -1;
						}
					}
				}
				if(lengthlastbranch>-1) {length=cumulative+lengthlastbranch;}
				debug("true "+target+","+(offset+1));
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
				if(numberoftimes==0||numberoftimes%8==0) {debug("true "+target+","+(offset+1));
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
				if(numberoftimes%8==0&&numberoftimes>0) {debug("true "+target+","+(offset+1));
							return length;}
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
