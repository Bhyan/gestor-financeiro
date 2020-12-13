package com.gestofinanceiro.services;

import br.ufrn.imd.stonks.framework.framework.model.DespesaAtivoFramework;
import br.ufrn.imd.stonks.framework.framework.service.EmailServiceAbstract;
import com.gestofinanceiro.helper.EmailConfig;
import com.gestofinanceiro.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService extends EmailServiceAbstract<DespesaAtivoFramework> {

    @Autowired
    private EmailConfig emailConfig;

    public Boolean enviarEmail(String mensagemEmail, Usuario usuario) {
        try {
            super.enviarEmail(mensagemEmail, usuario, "no-reply@stonks.com", "Relatório Gestor Financeiro");
            return true;
        } catch (MailException e) {
            System.out.print(e.getMessage());
        }

        return false;
    }

    @Override
    public String montarCorpoEmail(List<DespesaAtivoFramework> entities) {
        StringBuilder body = new StringBuilder("<h2>Seu relatório Gestor Financeiro</h2> <br/>");

        body.append("<table><tr><th> Despesa </th><th> Valor </th><th> Quantidade </th><th> Data da Transação </th></tr>");

        for (DespesaAtivoFramework ca : entities) {
            body.append("<tr> <th>")
                    .append(ca.getAtivo().getCodigo())
                    .append("</th>").append("<th>")
                    .append(ca.getValor())
                    .append("</th>").append("<th>")
                    .append(ca.getDataTransacao())
                    .append("</th> </tr>");
        }

        body.append("</table>");

        return body.toString();
    }

    @Override
    public JavaMailSenderImpl configurarHost() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfig.getHost());
        mailSender.setPort(emailConfig.getPort());
        mailSender.setUsername(emailConfig.getUsername());
        mailSender.setPassword(emailConfig.getPassword());

        return mailSender;
    }
}
