package br.com.fiap.fintech.teste;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.fintech.bean.Conta;
import br.com.fiap.fintech.bean.Usuario;
import br.com.fiap.fintech.dao.ContaDAO;
import br.com.fiap.fintech.dao.UsuarioDAO;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.factory.DAOFactory;
import br.com.fiap.fintech.sequencia.SequenciaUser;

public class TesteDAO {

	public static void main(String[] args) throws DBException {
		
		
		SequenciaUser novaSequencia = new SequenciaUser();
		
		List<Integer> listaCodUser = new ArrayList<>();
		listaCodUser = novaSequencia.novoCodigoUser(listaCodUser);
		int cd_user = listaCodUser.get(listaCodUser.size() - 1);
		listaCodUser = novaSequencia.novoCodigoUser(listaCodUser);
		int cd_conta = listaCodUser.get(listaCodUser.size() - 1);
		
		
		listaCodUser = novaSequencia.novoCodigoUser(listaCodUser);
		int cd_user2 = listaCodUser.get(listaCodUser.size() - 1);
		listaCodUser = novaSequencia.novoCodigoUser(listaCodUser);
		int cd_conta2 = listaCodUser.get(listaCodUser.size() - 1);
        		
		UsuarioDAO userDao = DAOFactory.getUsuarioDAO();
		ContaDAO contaDao = DAOFactory.getContaDAO();
		
		Usuario nina = new Usuario(cd_user2, "Nina", "39160202858", "ninatheprincess", "nina@gmail.com", "123456");

		Conta contaNina = new Conta (cd_conta2,0,0, "Ativada!");
		
		nina.setConta(contaNina);
		contaNina.setCd_usuario(nina);
		userDao.insert(nina);
		contaDao.insert(contaNina);
		
		userDao.setContaUsuario(cd_conta2, cd_user2);
		contaDao.setUsuarioConta(cd_user2, cd_conta2);
		
		
		
//		System.out.println(userDao.getById(20).getConta().getCodigo());
//		nina = userDao.getById(29);
//		nina.setNome("Nath");
//		
//		userDao.update(nina, contaNina.getCodigo());
//		userDao.updateContaUser(8, 1);
//		System.out.println(userDao.getById(29).getCodigo());
		
		
		
//		nina.setConta(contaNina);
//		contaNina.setCd_usuario(nina);
//
//		contaDao.setUsuarioConta(contaNina.getCodigo(), nina.getCodigo());
//		userDao.setContaUsuario(contaNina.getCodigo(), nina.getCodigo());
		
		
//		try {
//		
//			userDao.gerarContaUser();
//		
//	} catch (DBException e) {
//		e.printStackTrace();
//	}	
//		try {
//			
//			userDao.insert(usuario2);
//			
//			System.out.println("Usuario cadastrado");
//			
//		} catch (DBException e) {
//			e.printStackTrace();
//		}
//		System.out.println(userDao.getById(7).getNome());
		
		
		

		
		
		
		
		
		
//		List<Usuario> lista	= userDao.getDetails();
//		for (Usuario item: lista) {
//			System.out.println(item.getNome() + " " + item.getCodigo());
//		}
//		
//		List<Conta> lista2 = contaDao.getAll();
//		for (Conta item: lista2) {
//			System.out.println(item.getNum() + " " + item.getCodigo());
//		}
		
	}

}
