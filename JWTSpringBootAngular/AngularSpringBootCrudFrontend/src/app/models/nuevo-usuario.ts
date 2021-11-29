export class NuevoUsuario {

    nombre: string = "";
    username: string = "";
    userpass: string = "";
    email: string = "";

    constructor(nombre:string, username:string, userpass:string, email:string){
        this.nombre = nombre;
        this.username = username;
        this.userpass = userpass;
        this.email = email;
    }

}
