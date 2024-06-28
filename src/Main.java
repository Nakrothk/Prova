import models.*;
import models.Aluno;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String caminhoArquivo = "C:\\Users\\autologon\\Desktop\\Prova\\Prova\\alunos.csv";
        String caminhoRelatorio = "C:\\Users\\autologon\\Desktop\\Prova\\Prova\\resumo.csv";

        List<Aluno> alunos = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            leitor.readLine();

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(";");

                int matricula = Integer.parseInt(dados[0].trim());
                String nome = dados[1].trim();

                double nota = Double.parseDouble(dados[2].trim().replace(",", "."));

                Aluno aluno = new Aluno(matricula, nome, nota);
                alunos.add(aluno);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("Erro de formato numérico ao processar o arquivo: " + e.getMessage());
            return;
        }

        int quantidadeAlunos = alunos.size();
        int aprovados = 0;
        int reprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaNotas = 0.0;

        for (Aluno aluno : alunos) {
            double nota = aluno.getNota();
            somaNotas += nota;

            if (nota >= 6.0) {
                aprovados++;
            } else {
                reprovados++;
            }

            if (nota < menorNota) {
                menorNota = nota;
            }

            if (nota > maiorNota) {
                maiorNota = nota;
            }
        }

        double mediaGeral = somaNotas / quantidadeAlunos;

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminhoRelatorio))) {
            escritor.write("Quantidade de alunos na turma: " + quantidadeAlunos);
            escritor.newLine();
            escritor.write("Quantidade de alunos aprovados: " + aprovados);
            escritor.newLine();
            escritor.write("Quantidade de alunos reprovados: " + reprovados);
            escritor.newLine();
            escritor.write("Menor nota da turma: " + menorNota);
            escritor.newLine();
            escritor.write("Maior nota da turma: " + maiorNota);
            escritor.newLine();
            escritor.write("Média geral da turma: " + mediaGeral);
        } catch (IOException e) {
            System.out.println("Erro ao gravar o arquivo de relatório: " + e.getMessage());
        }

        System.out.println("Relatório gerado com sucesso em: " + caminhoRelatorio);
    }
}
