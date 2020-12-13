const a = 10;
//a = 20; // TypeError trying to set a constant

let x = 10;
{
    let x = 20; // different scope
    print(x); // prints 20
}
print(x); // prints 10 from the outer scope

