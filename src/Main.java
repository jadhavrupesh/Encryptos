public class Main {
    public static void main(String[] args) throws Exception {
        Encryptos encryptos = new Encryptos();
        String encryptedString = encryptos.encrypt("jadhavrupesh22@oksbi", "W9swVjAyfCiygsjubFLm9ZLFH7Sndlg21po6hGEc9io=");
        String decryptedString = encryptos.decrypt("YxTU84lwLaqjW5rBrDAnO9hvNiBnbDMXYgpj1zjOU6ptfVsQK+Pc4Q95Y/xeWkK2", "W9swVjAyfCiygsjubFLm9ZLFH7Sndlg21po6hGEc9io=");
        System.out.println("Hello world! encryptedString = " + encryptedString);
        System.out.println("Hello world! decryptedString = " + decryptedString);
    }
}