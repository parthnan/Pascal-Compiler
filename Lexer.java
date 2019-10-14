package enshud.s1.lexer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.PrintWriter;

public class Lexer {
	
	
	String printline(int lineno,int i) {
		String line = tokens[i][1];
		line = line.concat("\t");
		line = line.concat(tokens[i][2]);
		line = line.concat("\t");
		line = line.concat(tokens[i][0]);
		line = line.concat("\t");
		line = line.concat(Integer.toString(lineno));
		line = line.concat("\n");
		return line;
	}
	
	boolean isAlpha(byte b) {   
		char c=(char) (b & 0xFF);
		if( (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
			return true;
		else
			return false;
	}
	
	boolean isNumber(byte b) {   
		char c=(char) (b & 0xFF);
		if( (c >= '0' && c <= '9') )
			return true;
		else
			return false;
	}
	
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
	
	public static void main(final String[] args) {
		// normalの確認
		try {
			new Lexer().run("data/pas/normal01.pas", "tmp/out1.ts");
			new Lexer().run("data/pas/normal02.pas", "tmp/out2.ts");
			new Lexer().run("data/pas/normal03.pas", "tmp/out3.ts");
		}
		catch (Exception e) {
			System.err.println("File not found");
		}
	}


	public void run(final String inputFileName, final String outputFileName) {
		
		String s = "";
		try {
			/*String content = new Scanner(new File(inputFileName)).useDelimiter("\\Z").next();
			StringTokenizer lines = new StringTokenizer(content, "\n");
			int lineno = 0;
			while (lines.hasMoreTokens()) {
				++lineno;
				StringTokenizer words = new StringTokenizer(lines.nextToken(), " {}\t") ;
				while (words.hasMoreTokens()){
					String next=words.nextToken();  int length=next.length();
					code(next) ;
					number(next);
				}*/
			
				InputStream is = new BufferedInputStream(new FileInputStream(inputFileName));
				byte[] c = new byte[10240];
				int lineno = 1,readChars = 0,j=0;
				boolean empty = true;
		        while ((readChars = is.read(c)) != -1) {
		            empty = false;
		            for (int i = 0; i < readChars; ++i) {
		                
		                
		                if (c[i] == 'a') {
		                	if (i < readChars-2&& !isAlpha(c[i+3])) {
		                		if (c[i+1] == 'n'&&c[i+2] == 'd') {
				                	s = s.concat(printline(lineno,0));
				                	i+=3;
				                	}
			                }
		                	if (i < readChars-4&& !isAlpha(c[i+5])) {
		                		if (c[i+1] == 'r'&&c[i+2] == 'r'&&c[i+3] == 'a'&&c[i+4] == 'y') {
				                	s = s.concat(printline(lineno,1));
				                	i+=5;
				                	}
			                }		                	
		                }
		                
		                else if (c[i] == 'b') {
		                	if (i < readChars-4 && !isAlpha(c[i+5])) {
		                		if (c[i+1] == 'e'&&c[i+2] == 'g'&&c[i+3] == 'i'&&c[i+4] == 'n') {
				                	s = s.concat(printline(lineno,2));
				                	i+=5;
				                	}
			                }
		                	if (i < readChars-6&& !isAlpha(c[i+7])) {
		                		if (c[i+1] == 'o'&&c[i+2] == 'o'&&c[i+3] == 'l'&&c[i+4] == 'e'&&c[i+5] == 'a'&&c[i+6] == 'n') {
				                	s = s.concat(printline(lineno,3));
				                	i+=7;
				                	}
			                }		                	
		                }
		                
		                else if (c[i] == 'c') {
		                	if (i < readChars-3 && !isAlpha(c[i+4])) {
		                		if (c[i+1] == 'h'&&c[i+2] == 'a'&&c[i+3] == 'r') {
				                	s = s.concat(printline(lineno,4));
				                	i+=4;
				                	}
			                }	                	
		                }
		                
		                else if (c[i] == 'd') {
		                	if (i < readChars-2 && !isAlpha(c[i+3])) {
		                		if (c[i+1] == 'i'&&c[i+2] == 'v') {
				                	s = s.concat(printline(lineno,5));
				                	i+=3;
				                	}
			                }	
		                	if (i < readChars-1 && !isAlpha(c[i+2])) {
		                		if (c[i+1] == 'o') {
				                	s = s.concat(printline(lineno,6));
				                	i+=2;
				                	}
			                }	                	
		                }
		                
		                else if (c[i] == 'e') {
		                	if (i < readChars-3 && !isAlpha(c[i+4])) {
		                		if (c[i+1] == 'l'&&c[i+2] == 's'&&c[i+3] == 'e') {
				                	s = s.concat(printline(lineno,7));
				                	i+=4;
				                	}
			                }	       
		                	if (i < readChars-2&& !isAlpha(c[i+3])) {
		                		if (c[i+1] == 'n'&&c[i+2] == 'd') {
				                	s = s.concat(printline(lineno,8));
				                	i+=3;
				                	}
			                }
		                }
		                
		                else if (c[i] == 'f') {
		                	if (i < readChars-4 && !isAlpha(c[i+5])) {
		                		if (c[i+1] == 'a'&&c[i+2] == 'l'&&c[i+3] == 's'&&c[i+4] == 'e') {
				                	s = s.concat(printline(lineno,9));
				                	i+=5;
				                	}
			                }                	
		                }
		                
		                else if (c[i] == 'i') {
		                	if (i < readChars-1 && !isAlpha(c[i+2])) {
		                		if (c[i+1] == 'f') {
				                	s = s.concat(printline(lineno,10));
				                	i+=2;
				                	}
			                }	  
		                	if (i < readChars-6&& !isAlpha(c[i+7])) {
		                		if (c[i+1] == 'n'&&c[i+2] == 't'&&c[i+3] == 'e'&&c[i+4] == 'g'&&c[i+5] == 'e'&&c[i+6] == 'r') {
				                	s = s.concat(printline(lineno,11));
				                	i+=7;
				                	}
			                }	
		                }
		                
		                else if (c[i] == 'm') {
		                	if (i < readChars-2 && !isAlpha(c[i+3])) {
		                		if (c[i+1] == 'o'&&c[i+2] == 'd') {
				                	s = s.concat(printline(lineno,12));
				                	i+=3;
				                	}
			                }                	
		                }
		                
		                else if (c[i] == 'n') {
		                	if (i < readChars-2 && !isAlpha(c[i+3])) {
		                		if (c[i+1] == 'o'&&c[i+2] == 't') {
				                	s = s.concat(printline(lineno,13));
				                	i+=3;
				                	}
			                }                	
		                }
		                
		                else if (c[i] == 'o') {
		                	if (i < readChars-1 && !isAlpha(c[i+2])) {
		                		if (c[i+1] == 'f') {
				                	s = s.concat(printline(lineno,14));
				                	i+=2;
				                	}
			                }	              
		                	if (i < readChars-1 && !isAlpha(c[i+2])) {
		                		if (c[i+1] == 'r') {
				                	s = s.concat(printline(lineno,15));
				                	i+=2;
				                	}
			                }	 
		                }
		                
		                else if (c[i] == 'p') {
		                	if (i < readChars-8 && !isAlpha(c[i+9])) {
		                		if (c[i+1] == 'r'&&c[i+2] == 'o'&&c[i+3] == 'c'&&c[i+4] == 'e'&&c[i+5] == 'd'&&c[i+6] == 'u'&&c[i+7] == 'r'&&c[i+8] == 'e') {
				                	s = s.concat(printline(lineno,16));
				                	i+=9;
				                	}
			                }	              
		                	if (i < readChars-6&& !isAlpha(c[i+7])) {
		                		if (c[i+1] == 'r'&&c[i+2] == 'o'&&c[i+3] == 'g'&&c[i+4] == 'r'&&c[i+5] == 'a'&&c[i+6] == 'm') {
				                	s = s.concat(printline(lineno,17));
				                	i+=7;
				                	}
			                }		 
		                }
		                
		                else if (c[i] == 'r') {
		                	if (i < readChars-5 && !isAlpha(c[i+6])) {
		                		if (c[i+1] == 'e'&&c[i+2] == 'a'&&c[i+3] == 'd'&&c[i+4] == 'l'&&c[i+5] == 'n') {
				                	s = s.concat(printline(lineno,18));
				                	i+=6;
				                	}
			                }                	
		                }
		                
		                else if (c[i] == 't') {
		                	if (i < readChars-3 && !isAlpha(c[i+4])) {
		                		if (c[i+1] == 'h'&&c[i+2] == 'e'&&c[i+3] == 'n') {
				                	s = s.concat(printline(lineno,19));
				                	i+=4;
				                	}
		                		else if (c[i+1] == 'r'&&c[i+2] == 'u'&&c[i+3] == 'e') {
				                	s = s.concat(printline(lineno,20));
				                	i+=4;
				                	}
			                }	                              				 
		                }
		                
		                else if (c[i] == 'v') {
		                	if (i < readChars-2 && !isAlpha(c[i+3])) {
		                		if (c[i+1] == 'a'&&c[i+2] == 'r') {
				                	s = s.concat(printline(lineno,21));
				                	i+=3;
				                	}
			                }                	
		                }
		                
		                else if (c[i] == 'w') {
		                	if (i < readChars-4 && !isAlpha(c[i+5])) {
		                		if (c[i+1] == 'h'&&c[i+2] == 'i'&&c[i+3] == 'l'&&c[i+4] == 'e') {
				                	s = s.concat(printline(lineno,22));
				                	i+=5;
				                	}
			                }  
		                	else if (i < readChars-6 && !isAlpha(c[i+7])) {
		                		if (c[i+1] == 'r'&&c[i+2] == 'i'&&c[i+3] == 't'&&c[i+4] == 'e'&&c[i+5] == 'l'&&c[i+6] == 'n') {
				                	s = s.concat(printline(lineno,23));
				                	i+=7;
				                	}
			                }  
		                }
		                
		                else if (c[i] == '\"') {
		                	   j=0;
		                	   
		                	   s = s.concat("\"");
		                	   while(c[i+j+1]!='"'&&(i+j)<readChars-1)  {
		                		   j++;
		                		   s = s.concat(Character.toString((char) (c[i+j] & 0xFF)));
		                	   }        	
		                	   i+=j+1;
		                	   s = s.concat("\"\tSSTRING\t45\t");
		                	   s = s.concat(Integer.toString(lineno));
		                	   s = s.concat("\n");
		                }
		                
		                else if (c[i] == '\'') {
		                	   j=0;
		                	   s = s.concat("'");
		                	   while(c[i+j+1]!='\''&&(i+j)<readChars-1)  {
		                		   j++;
		                		   s = s.concat(Character.toString((char) (c[i+j] & 0xFF)));
		                	   }        	
		                	   i+=j+1;
		                	   s = s.concat("'\tSSTRING\t45\t");
		                	   s = s.concat(Integer.toString(lineno));
		                	   s = s.concat("\n");
		                }
		                
		                else if (isNumber(c[i])) {
		                	   j=0;   
		                	   while(isNumber(c[i+j])&& (i+j)<readChars-1)  {
		                		   s = s.concat(Character.toString((char) (c[i+j] & 0xFF)));
		                		   j++;
		                	   }
		                	   i+=j-1;
		                	   s = s.concat("\tSCONSTANT\t44\t");
		                	   s = s.concat(Integer.toString(lineno));
		                	   s = s.concat("\n");
		                }
		                
		                if (c[i] == '+') {
		                	s = s.concat(printline(lineno,30));
		                }
		                else if (c[i] == '-') {
		                	s = s.concat(printline(lineno,31));
		                }
		                else if (c[i] == '*') {
		                	s = s.concat(printline(lineno,32));
		                }
		                else if (c[i] == '/') {
		                	s = s.concat(printline(lineno,43));
		                }
		                else if (c[i] == ':') {
		                	if (i < readChars-1 && c[i+1] == '=') {
			                	s = s.concat(printline(lineno,40));
			                	i++;} 
		                	else {
		                		s = s.concat(printline(lineno,38));}
		                }
		                else if (c[i] == ';') {
		                	s = s.concat(printline(lineno,37));
		                }
		                else if (c[i] == '(') {
		                	s = s.concat(printline(lineno,33));
		                }
		                else if (c[i] == ')') {
		                	s = s.concat(printline(lineno,34));
		                }
		                else if (c[i] == '[') {
		                	s = s.concat(printline(lineno,35));
		                }
		                else if (c[i] == ']') {
		                	s = s.concat(printline(lineno,36));
		                }
		                else if (c[i] == '{') {
		                	while(c[i] != '}'&&i!=readChars-1) {
		                		i++;
		                	}
		                }
		                else if (c[i] == '.') {
		                	if (i < readChars-1 && c[i+1] == '.') {
			                	s = s.concat(printline(lineno,39));
			                	i++;}
			                
		                	else{s = s.concat(printline(lineno,42));	}	                	
		                }
		                else if (c[i] == ',') {
		                	s = s.concat(printline(lineno,41));
		                }
		                else if (c[i] == '=') {
		                	s = s.concat(printline(lineno,24));
		                }
		                else if (c[i] == '<') {
		                	if (i < readChars-1&&c[i+1] == '>') {         		
		                		s = s.concat(printline(lineno,25));
		                		i++;}
		                	else if (i < readChars-1&&c[i+1] == '=') {
				                s = s.concat(printline(lineno,27));
				                i++;}
			              
		                	else{s = s.concat(printline(lineno,26));}		                	
		                }
		                else if (c[i] == '>') {
		                	if (i < readChars-1&&c[i+1] == '=') {
				                	s = s.concat(printline(lineno,28));
				                	i++;
			                }
		                	else{s = s.concat(printline(lineno,29));	}	                	
		                }
		                else if (c[i] == '\n') {
		                    ++lineno;
		                }
		                
		                
		                else if (isAlpha(c[i])) {
		                		
		                	   j=0;
		                	   while((isAlpha(c[i+j])||isNumber(c[i+j]))&& (i+j)<readChars-1)  {
		                		   s = s.concat(Character.toString((char) (c[i+j] & 0xFF)));
		                		   j++;
		                	   }    
		                	   i+=j-1;
		                	   s = s.concat("\tSIDENTIFIER\t43\t");
		                	   s = s.concat(Integer.toString(lineno));
		                	   s = s.concat("\n");
		                }
		               
		                
		            }
		        }
		        s=s.substring(0,s.length()-1);
		        //System.out.println(s);
		        try (PrintWriter out = new PrintWriter(outputFileName)) {
		            out.println(s);
		        }
		        System.out.println("OK");
		        is.close();	
		        	
	    }  
	    catch (Exception e) {
	    	try (PrintWriter out = new PrintWriter(outputFileName)) {
	            out.println(s);
	        }
	    	catch (Exception b) {System.err.println("File not found");}
	    	System.err.println("File not found");
	    }

	}
}


