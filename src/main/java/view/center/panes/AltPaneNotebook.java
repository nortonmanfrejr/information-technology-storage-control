package view.center.panes;

import model.abstractTableModel.NotebookTBModel;
import service.dispatcher.DispatcherNotebook;
import service.dispatcher.conveyor.ConveyorImpl;
import service.dispatcher.conveyor.ConveyorInterface;
import view.requierements.ComponentsRequierementsImpl;
import view.requierements.StaticFieldRequierementsVar;

import javax.swing.*;
import java.awt.*;

public class AltPaneNotebook implements AltPaneInterface {
    private ConveyorInterface conveyorInterface = new ConveyorImpl(new DispatcherNotebook());
    private final NotebookTBModel notebookTBModel = new NotebookTBModel(conveyorInterface.read());

    private ComponentsRequierementsImpl f = new ComponentsRequierementsImpl();

    @Override
    public JPanel northTitle() {
        JLabel tituloPagina = new JLabel("Gerenciador - Notebook");
        tituloPagina.setFont(new Font("Tahoma",Font.BOLD,34));

        JPanel p = new JPanel();
        p.add(tituloPagina);
        p.setBorder(BorderFactory.createBevelBorder(0));

        return p;
    }

    @Override
    public JPanel centerField() {
        JPanel centerPanel = new JPanel(new GridBagLayout());

        f.addComponentIntoPaneIfGridPane(centerPanel,"Patrimonio :", f.txf_patrimonio(),0,0);
        f.addComponentIntoPaneIfGridPane(centerPanel,"Service Tag:", f.txf_servicetag(),0,1);
        f.addComponentIntoPaneIfGridPane(centerPanel, "Marca :", f.cb_marca(),0,2);
        f.addComponentIntoPaneIfGridPane(centerPanel,"Modelo :", f.cb_modelo(),0,3);
        f.addComponentIntoPaneIfGridPane(centerPanel,"Andar :",f.cb_andar(),1,0);
        f.addComponentIntoPaneIfGridPane(centerPanel,"Departamento :", f.cb_departamento(),1,1);
        f.addComponentIntoPaneIfGridPane(centerPanel,"Setor :",f.cb_setor(),1,2);
        f.addComponentIntoPaneIfGridPane(centerPanel,"Status :",f.cb_status("Ativo","Sucata","Manutenção","Emprestado"),1,3);
        f.addComponentIntoPaneIfGridPane(centerPanel,"GPU :",f.cb_placaDeVideo(),2,0);
        f.addComponentIntoPaneIfGridPane(centerPanel,"RAM :",f.cb_memoriaRAM(),2,1);
        f.addComponentIntoPaneIfGridPane(centerPanel,"Armazenamento :",f.cb_tipoArmazenamento(),2,2);
        f.addComponentIntoPaneIfGridPane(centerPanel,"Sistema :",f.cb_sistemaOperacional(),2,3);
        f.addComponentIntoPaneIfGridPane(centerPanel,"Empréstimo",f.cb_disponibilidadeEmprestimo(),3,0);
        f.addComponentIntoPaneIfGridPane(centerPanel,"Mac Adress :", f.txf_macAdress(),3,1);
        f.addComponentIntoGridPaneNoLabel(centerPanel, new JLabel("Observação"), 3,2);
        f.addComponentIntoGridPaneNoLabel(centerPanel,f.txa_observacao(),3,3);

        return centerPanel;
    }

    @Override
    public JPanel eastButton(ConveyorInterface conveyorInterface) {
        JPanel gridPanel = new JPanel(new GridBagLayout());

        gridPanel.add(f.factoryJButton("Adicionar",e -> {
            conveyorInterface.insert();
            notebookTBModel.reloadTable(conveyorInterface.read());
        }), f.fieldConstraints(0,0));

        gridPanel.add(f.factoryJButton("Deletar",e -> {
            conveyorInterface.delete();
            notebookTBModel.reloadTable(conveyorInterface.read());
        }), f.fieldConstraints(0,1));

        gridPanel.add(f.factoryJButton("Listar",e -> {
            notebookTBModel.reloadTable(conveyorInterface.read());
        }), f.fieldConstraints(1,0));

        gridPanel.add(f.factoryJButton("Buscar", e -> {
            notebookTBModel.reloadTable(conveyorInterface.search());
            StaticFieldRequierementsVar.txf_campoDeBusca.setText("Campo de Busca...");
        }), f.fieldConstraints(2,0));

        gridPanel.add(f.factoryJButton("Alterar",e -> {
            conveyorInterface.update();
            notebookTBModel.reloadTable(conveyorInterface.read());
        }), f.fieldConstraints(1,1));

        JPanel eastPanel = new JPanel(new BorderLayout());

        eastPanel.add(f.txf_campoDeBusca(), BorderLayout.NORTH);
        eastPanel.add(gridPanel, BorderLayout.CENTER);

        eastPanel.setBorder(BorderFactory.createBevelBorder(0));

        eastPanel.setMinimumSize(new Dimension(270,300));
        return eastPanel;
    }

    @Override
    public JScrollPane southTable() {
        JTable table = new JTable(notebookTBModel);

        JScrollPane southPanel = new JScrollPane(table);
        southPanel.setPreferredSize(new Dimension(400,500));

        return southPanel;
    }
}
