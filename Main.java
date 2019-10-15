import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bem vindo ao IResearch");
        //Scanner input = new Scanner(System.in);
        int option;

        String[][] netWork = new String[1000][1000];
        for (int i = 0; i < 1000; i++){
            for (int j = 0; j < 1000; j++){
                netWork[i][j] = "-";
                if (j == 7)
                    netWork[i][j] = "";
            }
        }

        String[][] communitys = new String[1000][1000];
        for (int i = 0; i < 1000; i++){
            for (int j = 0; j < 1000; j++){
                communitys[i][j] = "-";
            }
        }

        int user = -1;

        do {

            option = glossario(user);
            if (option == 1)
                user = creatAccount(netWork);

            else if (option == 2)
                modifyProfile(netWork, user);

            else if (option == 3)
                addFriend(netWork, user);

            else if (option == 4)
                sendMensage(netWork, user);

            else if (option == 5)
                creatCommunity(communitys, netWork, user);

            else if (option == 6){
                addMember(communitys, netWork,user);
            }

            else if (option == 7){
                information(netWork, communitys);
            }

            else if (option == 8){
                user = remove(netWork, communitys,user);
            }

            else if (option == 9)
                user = logInto(netWork);

            else if (option == 10)
                notification(netWork, user);

            else if (option == 11)
                publicCommunity(communitys, netWork, user);

            else if (option == 12)
                deBug(netWork);

            if (option == -1 && user != -1) {
                option = 0;
                user = -1;
            }
        } while(option != -1);
    }

    private static void publicCommunity(String[][] communitys, String[][] netWork, int user) {
        Scanner input = new Scanner(System.in);
        String name = netWork[user][0];
        System.out.println("qual o nome da comunidade");
        String communityName = input.nextLine();
        int pos = get(communitys,communityName, 0);
        if (pos == -1){
            System.out.println("Comunidade não encontrada");
            return;
        }

        if (checkCommunityMember(communitys, pos,name)){
            System.out.println("entre com a menssagem");
            String mensage = input.nextLine();
            communitys[pos][3] += "\n"+name+" disse: "+mensage;
        }
        else
            System.out.println("Comunidade inexistente");
    }

    private static boolean checkCommunityMember(String[][] communitys,int community,String name) {
        for (int i = 4; i < 1000; i++){
            if (communitys[community][i].equals(name))
                return true;
        }
        return communitys[community][2].equals(name);
    }

    private static int remove(String[][] netWork, String[][] community,int user) {
        Scanner input = new Scanner(System.in);
        System.out.println("Certeza que deseja apagar sua conta!?\nsim ou não");
        String confirm = input.nextLine();
        if (confirm.equals("não"))
            return user;
        String name = netWork[user][0];
        for(int i = 0; i < 1000; i++){
            netWork[user][i] = "-";
        }
        dellInFriends(netWork,name);
        dellInCommunity(community, name);
        System.out.println("Usuario removido com sucesso");
        return -1;
    }

    private static void dellInCommunity(String[][] community, String name) {
        for (int i = 0; i < 1000; i++){
            for (int j = 4; j < 1000; j++) {
                if (community[i][j].equals(name)){
                    community[i][j] = "-";
                    break;
                }
            }
        }
    }

    private static void dellInFriends(String[][] netWork, String name) {
        for (int i = 0; i < 1000; i++){
            for (int j = 9; j < 1000; j++) {
                if (netWork[i][j].equals(name)){
                    netWork[i][j] = "-";
                    break;
                }
            }
        }
    }

    private static void information(String[][] netWork, String[][] comunnitys) {
        Scanner input = new Scanner(System.in);
        System.out.println("1 => Obter informação de um usuario");
        System.out.println("2 => Obter informação de uma comunidade");
        int option = input.nextInt();
        if (option == 1)
            getUserInformation(netWork);
        else if (option == 2)
            getCommunityInformation(comunnitys);
    }

    private static void getCommunityInformation(String[][] communitys) {
        Scanner input = new Scanner(System.in);
        System.out.println("Entre com o nome da comunidade");
        String name = input.nextLine();
        int pos = get(communitys, name, 0);
        if (pos == -1) {
            System.out.println("esta comunidade não existe");
            return;
        }
        System.out.println("Qual informação deseja?");
        System.out.println("1 => Descrição");
        System.out.println("2 => Nome do administrador");
        System.out.println("3 => Mural");
        System.out.println("4 => Membros");

        int option = input.nextInt();
        if (option == 4){
            for (int i = 4; i < 1000; i++){
                if (!communitys[pos][i].equals("-")){
                    System.out.println(communitys[pos][i]);
                }
            }
            return;
        }
        System.out.println(communitys[pos][option]);
    }

    private static void getUserInformation(String[][] netWork) {
        Scanner input = new Scanner(System.in);
        System.out.println("Entre com o nome do usuario que você deseja obter informações");
        System.out.println("incluindo você mesmo");
        String name = input.nextLine();
        int check = get(netWork, name, 0);
        if (check == -1){
            System.out.println("Este usuario não existe");
            return;
        }
        System.out.println("Qual dado deseja ver?");
        System.out.println("3 => nome");
        System.out.println("4 => idada");
        System.out.println("5 => sexo");
        System.out.println("6 => estado civil");
        System.out.println("7 => ver amigos");
        int option = input.nextInt();
        if (option == 7){
            System.out.println("Amigos");
            for (int i = 9; i < 1000; i++){
                if (!netWork[check][i].equals("-")){
                    System.out.println(netWork[check][i]);
                }
            }
        }
        else{
            System.out.println(netWork[check][option]);
        }
    }

    private static void addMember(String[][] communitys, String[][] netWork,int user) {
        Scanner input = new Scanner(System.in);
        System.out.println("Entre com o nome da comunidade");
        String name = input.nextLine();
        int pos = get(communitys,name,0);
        if (pos == -1){
            System.out.println("Comunidade não encontrada");
            return;
        }
        System.out.println("Entre com o nome do novo membro");
        String newMember = input.nextLine();
        int check = get(netWork, newMember,0);
        if (check == -1){
            System.out.println("Usuario inexistente");
            return;
        }
        if (netWork[user][0].equals(communitys[pos][2])){
            for (int i = 5; i < 1000; i++){
                if (communitys[pos][i].equals("-")){
                    communitys[pos][i] = newMember;
                    break;
                }
            }
            System.out.println("Usuario adicionado com sucesso");
        }
        else
            System.out.println("você não é adiministrador dessa comunidade");


    }

    private static void notification(String[][] netWork, int user) {
        Scanner input = new Scanner(System.in);
        System.out.println("1 => checar solicitações de amizade");
        System.out.println("2 => checar mensagens");
        int option = input.nextInt();
        if (option == 1)
            checkFriendNotification(netWork, user);
        else if (option ==2)
            checkMensageNotification(netWork, user);
    }

    private static void checkMensageNotification(String[][] netWork, int user) {
        Scanner input = new Scanner(System.in);
        if (netWork[user][7].equals("")){
            System.out.println("não a mensagens");
            return;
        }
        System.out.println(netWork[user][7]);
        System.out.println("Deseja responder a menssagem?\nsim ou não");
        String option = input.nextLine();
        if (option.equals("sim"))
            sendMensage(netWork,user);
        System.out.println("deseja esvaziar suar menssagens?");
        option = input.nextLine();
        if (option.equals("sim"))
            netWork[user][7] = "";
    }

    private static void checkFriendNotification(String[][] netWork, int user) {
        Scanner input = new Scanner(System.in);
        String aux = netWork[user][8];
        if (aux.equals("-")){
            System.out.println("Não a solicitações de amizade");
            return;
        }
        System.out.println(aux);
        System.out.println("Apos o fim dessa operação todos os pedidos de amizade serão apagados");
        int option = 0;
        while(option != 2){
            System.out.println("entre com o nome do ususario que deseja confirmar o pedido de amizade(digite - para nem um)");
            String newFriend = input.nextLine();
            if (newFriend.equals("-")) {
                netWork[user][8] = "";
                return;
            }
            confirmFriendRequest(netWork, user, newFriend);
            System.out.println("1 => continuar\n2=>finalizar");
            option = input.nextInt();
            if (option == 1)
                newFriend = input.nextLine();
        }
        netWork[user][8] = "-";

    }

    private static void confirmFriendRequest(String[][] netWork, int user, String newFriend) {
        int pos = get(netWork,newFriend,0);
        if (pos == -1) {
            System.out.println("Usuario não encontrado");
            return;
        }
        for (int i = 9; i < 1000; i++){
            if (netWork[user][i].equals("-")){
                netWork[user][i] = newFriend;
                break;
            }
        }
        for (int i = 9; i < 1000; i++){
            if (netWork[pos][i].equals("-")){
                netWork[pos][i] = netWork[user][0];
                break;
            }
        }
        System.out.println("Solicitação confirmada com scesso");
    }

    private static void creatCommunity(String[][] communitys, String[][] netWork, int pos) {
        Scanner input = new Scanner(System.in);

        int confirm = 0;
        String name = "";

        while(confirm != -1){
            System.out.println("Entre com o nome da nova comunidade:");
            name = input.nextLine();
            confirm = get(communitys,name,0);
            if (confirm != -1)
                System.out.println("Nome dessa comunidade ja existe");
        }

        for (int i = 0; i < 1000; i++){
            if (communitys[i][0].equals("-")){
                communitys[i][0] = name;
                System.out.println("Entre com a descrição:");
                String description = input.nextLine();
                communitys[i][1] = description;

                String adm = netWork[pos][0];
                communitys[i][2] = adm;
                break;
            }
        }

    }

    private static void deBug(String[][] netWork) {
        for (int i = 0; i < 1000; i++){
            if (!netWork[i][0].equals("-")){
                System.out.println("usuario: "+netWork[i][0]);
                System.out.println("login: "+netWork[i][1]);
                System.out.println("senha: "+netWork[i][2]);
                System.out.println("nome :"+netWork[i][3]);
                System.out.println("idade: "+netWork[i][4]);
                System.out.println("sexo: "+netWork[i][5]);
                System.out.println("estado civil: "+netWork[i][6]);
                System.out.println("mensagem pendente: "+netWork[i][7]);
                System.out.println("Solicitação de amizade "+netWork[i][8]);
                int j = 9;
                System.out.println("amigos:");
                while(!netWork[i][j].equals("-")){
                    System.out.println(netWork[i][j]);
                    j++;
                }
            }
        }
    }

    private static void sendMensage(String[][] netWork, int user) {
        Scanner input = new Scanner(System.in);
        System.out.println("Para quem deseja enviar ensagem?");
        String toSend = input.nextLine();
        int posSend = get(netWork,toSend,0);
        if (posSend == -1)
            System.out.println("Usuario não encontrado");
        else{
            System.out.println("Entre com a mensagem:");
            String newData = input.nextLine();
            newData = "\n"+netWork[user][0]+" disee:\n"+newData;
            netWork[posSend][7] += newData;
        }
    }

    private static void addFriend(String[][] netWork, int user) {
        Scanner input = new Scanner(System.in);
        System.out.println("entre com o nome do usuario que deseja adicionar");
        String newFriend = input.nextLine();
        int pos = get(netWork,newFriend,0);
        if (pos == -1)
            System.out.println("Usuario não encontrado");
        else{
            if (isFriend(netWork,user,newFriend)){
                System.out.println("Este usuario ja é seu amigo");
                return;
            }
            netWork[pos][8] += "\n"+netWork[user][0];
            System.out.println("Solicitação de amizade enviada com sucesso");
        }
    }

    private static boolean isFriend(String[][] netWork, int user, String newFriend) {
        for (int i = 9; i < 1000; i++){
            if(netWork[user][i].equals(newFriend))
                return true;
        }
        return false;

    }

    private static void modifyProfile(String[][] netWork, int user) {
        Scanner input = new Scanner(System.in);
        if (user == -1)
            System.out.println("Usuario não encontrado");
        else{
            System.out.println("Oque deseja adicionar/alterar?\n");
            System.out.println("1 => login");
            System.out.println("2 => senha");
            System.out.println("3 => nome");
            System.out.println("4 => idade");
            System.out.println("5 => sexo");
            System.out.println("6 => estado civil");
            int toChange = input.nextInt();
            System.out.println("entre com o novo dado:");
            input.nextLine();
            String newData = input.nextLine();
            netWork[user][toChange] = newData;
        }
    }

    private static int logInto(String[][] netWork) {
        System.out.println("Login:");
        Scanner input = new Scanner(System.in);
        System.out.println("Entre com o login:");
        String login = input.nextLine();

        int pos = get(netWork, login, 1);
        if (pos == -1){
            System.out.println("Usuario não encontrado");
            return -1;

        }

        System.out.println("entre com a senha");
        String pass = input.nextLine();
        if (!pass.equals(netWork[pos][2])){
            System.out.println("Senha incorreta");
            return -1;
        }
        return pos;
    }

    private static int get(String[][] netWork, String getter, int i) {
        for (int j = 0; j < 1000; j++){
            if (netWork[j][i].equals(getter))
                return j;
        }
        return -1;
    }

    private static int creatAccount(String[][] netWork) {
        Scanner input = new Scanner(System.in);
        int confirm;
        String userName, login;

        do {
            System.out.println("Entre com o login");
            login = input.nextLine();
            confirm = check(netWork, login);
            if (confirm == 0)
                System.out.println("login ja existe");
        }while(confirm != 1);


        System.out.println("Entre com a senha");
        String password = input.nextLine();

        do {
            System.out.println("Entre com o nome de usuario");
            userName = input.nextLine();
            confirm = check(netWork, userName);
            if (confirm == 0)
                System.out.println("usuario ja existe");
        }while(confirm != 1);

        for (int i = 0; i < 1000; i++){
            if (netWork[i][0].equals("-")) {
                netWork[i][0] = userName;
                netWork[i][1] = login;
                netWork[i][2] = password;
                System.out.println("usuario adicionado com sucesso");
                return i;
            }
        }

        return -1;

    }

    private static int check(String[][] netWork, String comp) {
        for (int i = 0; i < 1000; i++){
            if (netWork[i][0].equals(comp))
                return 0;
        }
        return 1;
    }

    private static int glossario(int user) {
        if (user != -1){
            System.out.println("2 => Criar/Editar perfil");
            System.out.println("3 => Adicionar amigo");
            System.out.println("4 => Enviar mensagem");
            System.out.println("5 => Criar comunidade");
            System.out.println("6 => Adicionar membro");
            System.out.println("7 => Recuperar informações");
            System.out.println("8 => Remover conta");
            System.out.println("10 => para checar notificações");
            System.out.println("11 => Publicar no mural de uma comunidade");
        }
        else {
            System.out.println("1 => Criar Conta");
            System.out.println("9 => para fazer login");
        }
        System.out.println("-1 => sair");
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }
}
