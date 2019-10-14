program testBasic;
var i: integer;
    c: char;

procedure subproc(x: integer);
var  c: char;
begin
    c := 's';
    writeln(c);
    writeln(x);
end;

begin
    c := 'm';
    subproc(99);
    writeln(c);
end.
