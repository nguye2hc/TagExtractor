import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileMethod {

    static JFileChooser chooser = new JFileChooser();
    static File workingDirectory = new File(System.getProperty("user.dir"));
    static Map<String, Integer> map = new HashMap<>();
    static Set<String> words = new HashSet<>();
    static File selectedFile = null;

    public static File fileBrowser(JTextArea textArea) {
        try {
            chooser.setCurrentDirectory(workingDirectory);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                textArea.setText("");
                textArea.append(selectedFile.getName());
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Please choose a file!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return selectedFile;
    }
    public static File fileBrowser() {
        return selectedFile;
    }
    static void fileFilter(JTextArea textArea, File selectedFile) {

        ArrayList lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(selectedFile);
            while (scanner.hasNext()) {
                lines.add(scanner.next().replace("\t", "").replace(" ", "").
                        replace("-", "").replace("!", "").replace
                                (".", "").replace(",", "").replace
                                ("\t\t", "").replace(";", "").replace
                                ("(", "").replace(")", "").replace
                                ("'", "").replace("\"", "").
                        replace("\'", "").replace("_", "")
                        .replace("[", "").replace("]", "").replace("\\", "")
                        .replace("?", "").replace("\"\"\"", "")
                        .replace(":", "").replace("*", "").replace("{", "")
                        .replace("}", "").replace("", "").replace("'s", "").toLowerCase());
            }

            for (int i = 0; i < lines.size(); i++) {
                if (words.contains((String) lines.get(i)) == true) {
                    lines.remove(i);
                    i--;
                } else if (((String) lines.get(i)).length() < 3) {
                    lines.remove(i);
                    i--;
                }
            }
            map = (Map<String, Integer>) lines.parallelStream().collect(Collectors.groupingByConcurrent(w -> w, Collectors.counting()));
            for (Map.Entry<String, Integer> extractWord : map.entrySet()) {
                textArea.append("Tag:\t" + extractWord.getKey() + "\t\tFrequency:\t" + extractWord.getValue() + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    static void fileSave(JTextArea result)
    {
        String fileName = JOptionPane.showInputDialog(null,"Please enter your file name");

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath()+"\\src\\"+ fileName + ".txt");

        try
        {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file,CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            writer.write(result.getText());
            writer.close();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}