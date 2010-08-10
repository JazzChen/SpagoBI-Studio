/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 **/
package it.eng.spagobi.studio.documentcomposition.wizards;

import it.eng.spagobi.studio.documentcomposition.Activator;
import it.eng.spagobi.studio.documentcomposition.editors.DocumentCompositionEditor;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Document;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentComposition;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.DocumentsConfiguration;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameter;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Parameters;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.Refresh;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.RefreshDocLinked;
import it.eng.spagobi.studio.documentcomposition.editors.model.documentcomposition.bo.ParameterBO;
import it.eng.spagobi.studio.documentcomposition.wizards.pages.NewNavigationWizardDestinDocPage;
import it.eng.spagobi.studio.documentcomposition.wizards.pages.NewNavigationWizardMasterDocPage;
import it.eng.spagobi.studio.documentcomposition.wizards.pages.NewNavigationWizardPage;
import it.eng.spagobi.studio.documentcomposition.wizards.pages.util.DestinationInfo;

import java.util.HashMap;
import java.util.Vector;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class SpagoBINavigationWizard extends Wizard implements INewWizard{

	// workbench selection when the wizard was started
	protected IStructuredSelection selection;
	private ParameterBO bo = new ParameterBO();

	// the workbench instance
	protected IWorkbench workbench;

	// dashboard creation page
	private NewNavigationWizardPage newNavigationWizardPage;
	private NewNavigationWizardMasterDocPage newNavigationWizardMasterDocPage;
	private NewNavigationWizardDestinDocPage newNavigationWizardDestinDocPage;
	
	private String selectedMaster;

	public String getSelectedMaster() {
		return selectedMaster;
	}


	public void setSelectedMaster(String selectedMaster) {
		this.selectedMaster = selectedMaster;
	}

	public IStructuredSelection getSelection() {
		return selection;
	}


	public void setSelection(IStructuredSelection selection) {
		this.selection = selection;
	}
	public SpagoBINavigationWizard() {
		super();
	}


	@Override
	public void addPage(IWizardPage page) {
		// TODO Auto-generated method stub
		super.addPage(page);
	}
	
	private void completePageDataCollection(){
		if(newNavigationWizardDestinDocPage.isPageComplete()){
			DestinationInfo destinationInfo = new DestinationInfo();
			int destinCounter= newNavigationWizardDestinDocPage.getDestinCounter();
			int sel = newNavigationWizardDestinDocPage.getDestinationDocNameCombo().elementAt(destinCounter).getSelectionIndex();
			destinationInfo.setDocDestName(newNavigationWizardDestinDocPage.getDestinationDocNameCombo().elementAt(destinCounter).getItem(sel));
			
			
			//destinationInfo.setParamDestName(newNavigationWizardDestinDocPage.getDestinationInputParam().elementAt(destinCounter).getItem(selIn));
			String label = newNavigationWizardDestinDocPage.getDestinationInputParam().elementAt(destinCounter).getText();
			String urlName= (String)newNavigationWizardDestinDocPage.getDestinationInputParam().elementAt(destinCounter).getData(label);
			destinationInfo.setParamDestName(urlName);
			
			destinationInfo.setParamDefaultValue(newNavigationWizardDestinDocPage.getDestinationInputParamDefaultValue().elementAt(destinCounter));
			newNavigationWizardDestinDocPage.getDestinationInfos().add(destinationInfo);	
		}
	}
	
	private void redrawTable(){
		//*INSERISCE NELLA LISTA DELLE NAVIGATION LA NUOVA NAVIGAZIONE*/
		 
		Object objSel = selection.toList().get(0);
		Table listOfNavigations = (Table)objSel;
	    TableItem item = new TableItem(listOfNavigations, SWT.NONE);
		RGB rgb=new RGB(192,0,0);
		final Color color = new Color(listOfNavigations.getShell().getDisplay(), rgb);
	    item.setForeground(color);
	    item.setText(0, newNavigationWizardPage.getNavigationNameText().getText());

	    item.setText(1, newNavigationWizardMasterDocPage.getMasterDocName().getText());
      
	    StringBuffer dest = new StringBuffer();
	    
		Vector<DestinationInfo> destInfos = newNavigationWizardDestinDocPage.getDestinationInfos();
		for(int k =0; k<destInfos.size(); k++){
			DestinationInfo destInfo = destInfos.elementAt(k);
			String destinationDoc = destInfo.getDocDestName();
			String destParam = destInfo.getParamDestName();
			if(destinationDoc != null){
	    		dest.append((destInfos.elementAt(k)).getDocDestName());
	    		dest.append("(");
	    		dest.append(destParam);
	    		dest.append(")");
	    		if(k != destInfos.size()-1){
	    			dest.append(" - ");
	    		}
			}
			
		}

		item.setText(2, dest.toString());
    
	    listOfNavigations.getShell().redraw();
		////////////////////////////////////
		
	}
	
	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		boolean can = super.canFinish();
		return can;
	}


	@Override
	public boolean performFinish() {

		completePageDataCollection();

		redrawTable();
		
		//recupera da plugin oggetto DocumentComposition		
		DocumentComposition docComp = Activator.getDefault().getDocumentComposition();
		
		String masterLabel= newNavigationWizardMasterDocPage.getMasterLabel();

		DocumentsConfiguration docConf = docComp.getDocumentsConfiguration();
		if(docConf != null){
		    Vector documents = docConf.getDocuments();
		    if(documents != null){
		    	//aggiunge parametro OUT per doc master
		    	
    			Parameter masterParam = new Parameter(docComp);//viene generato id
    			fillNavigationOutParam(masterParam, documents);
		    	
		    	fillInNavigationParams(documents, masterParam);
		    	
		    }
		}
  
		IWorkbenchPage iworkbenchpage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		DocumentCompositionEditor editor= (DocumentCompositionEditor)iworkbenchpage.getActiveEditor();
		editor.setIsDirty(true);
	    return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("New navigation creation");
		
		this.workbench = workbench;
		this.selection = selection;
		
		newNavigationWizardPage = new NewNavigationWizardPage();
		newNavigationWizardMasterDocPage = new NewNavigationWizardMasterDocPage();
		newNavigationWizardDestinDocPage = new NewNavigationWizardDestinDocPage();
	}
	
	public void addPages() {
		super.addPages();
		newNavigationWizardPage = new NewNavigationWizardPage("New Navigation");
		addPage(newNavigationWizardPage);
		newNavigationWizardPage.setPageComplete(false);
		
		newNavigationWizardMasterDocPage = new NewNavigationWizardMasterDocPage("Master document");
		addPage(newNavigationWizardMasterDocPage);
		newNavigationWizardMasterDocPage.setPageComplete(false);
		
		newNavigationWizardDestinDocPage = new NewNavigationWizardDestinDocPage("Destination document");

		addPage(newNavigationWizardDestinDocPage);
		
	}
	
	private void fillNavigationOutParam(Parameter param , Vector documents){
		String master =newNavigationWizardMasterDocPage.getMasterDocName().getText();
		//String out =newNavigationWizardMasterDocPage.getMasterDocOutputParam().getText();
    	String masterParamLabel= newNavigationWizardMasterDocPage.getMasterDocOutputParam().getText();
    	String masterParamUrl= (String)newNavigationWizardMasterDocPage.getMasterDocOutputParam().getData(masterParamLabel);
    	if(masterParamUrl == null){//edited by user
    		masterParamUrl=masterParamLabel;
    		
    	}
		param.setSbiParLabel(masterParamUrl);
		
		param.setNavigationName(newNavigationWizardPage.getNavigationNameText().getText());
		param.setDefaultVal(newNavigationWizardMasterDocPage.getMasterDefaultValueOutputParam().getText());
		HashMap<String, String> docInfoUtil= newNavigationWizardDestinDocPage.getDocInfoUtil();
		param.setType("OUT");
		
		String masterLabel = newNavigationWizardMasterDocPage.getMasterLabel();
	    if(documents != null){			    
		    for (int i = 0; i< documents.size(); i++){
		    	Document doc = (Document)documents.elementAt(i);	
		    	if(doc.getParameters() == null){
		    		doc.setParameters(new Parameters());
		    	}
		    	Vector parameters = doc.getParameters().getParameter();
		    	if(doc.getParameters().getParameter() == null){
		    		doc.getParameters().setParameter(new Vector());
		    	}

		    	Parameter outputParam = bo.getDocOutputParameter(parameters, masterParamUrl);
		    	
		    	if(outputParam != null && outputParam.getSbiParLabel().equals(masterParamUrl)){
		    		param = outputParam;
		    		param.setDefaultVal(newNavigationWizardMasterDocPage.getMasterDefaultValueOutputParam().getText());
		    		return;
		    	}
				if(masterLabel != null && masterLabel.equals(doc.getSbiObjLabel())){
					//se doc master
					doc.getParameters().getParameter().add(param);
				}

		    }
	    }
	}
	
	private void fillRefreshes(Vector<RefreshDocLinked> refreshes, String docDest ,Parameter param){

		RefreshDocLinked refreshDocLinked = new RefreshDocLinked();

		String paramIn =param.getSbiParLabel();
		
		refreshDocLinked.setLabelDoc(docDest);
		refreshDocLinked.setLabelParam(paramIn);
		refreshDocLinked.setIdParam(param.getId());

		refreshes.add(refreshDocLinked);
	}
	private void fillInNavigationParams(Vector documents, Parameter masterParam){

		//cicla su destinazioni
		Vector<DestinationInfo> destInfos = newNavigationWizardDestinDocPage.getDestinationInfos();
		HashMap<String, String> docInfoUtil= newNavigationWizardDestinDocPage.getDocInfoUtil();
		for(int k =0; k<destInfos.size(); k++){
			DestinationInfo destInfo = destInfos.elementAt(k);
			String destinationDoc = destInfo.getDocDestName();
			
			//recupera da hashmap di utilit� la label corrispondente
			String destLabel = docInfoUtil.get(destinationDoc);
		    if(documents != null){			    
			    for (int i = 0; i< documents.size(); i++){
			    	Document doc = (Document)documents.elementAt(i);			    	
			    	Vector parameters = doc.getParameters().getParameter();
					if(destLabel != null && destLabel.equals(doc.getSbiObjLabel())){
						String paramName = destInfo.getParamDestName();
						Parameter param =bo.getDocInputParameterByLabel(parameters, paramName);
						if(param == null){
							param = new Parameter(Activator.getDefault().getDocumentComposition());//viene anche creato ID
							param.setType("IN");
							param.setSbiParLabel(paramName);
							param.setDefaultVal(destInfo.getParamDefaultValue().getText());
							parameters.add(param);
						}else{
							param.setDefaultVal(destInfo.getParamDefaultValue().getText());
						}
						
						Refresh refresh = masterParam.getRefresh();
						if(refresh == null){
							refresh = new Refresh();
						}
						Vector <RefreshDocLinked> refreshes = refresh.getRefreshDocLinked();
						if(refreshes == null){
							refreshes = new Vector<RefreshDocLinked>();
						}
						
						fillRefreshes(refreshes, destLabel, param);
						
						refresh.setRefreshDocLinked(refreshes);
						masterParam.setRefresh(refresh);
					}
			    }
		    }
		}

	}
}