package com.isoftstone.agiledev.web.springmvc.easyui;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.isoftstone.agiledev.web.DataOutputAdaptor;

public class EasyUIView extends MappingJacksonJsonView {
	private ObjectMapper objectMapper = new ObjectMapper();
	private boolean prefixJson = false;
	private JsonEncoding encoding = JsonEncoding.UTF8;
	
	private List<DataOutputAdaptor> dataOutputAdaptors = null;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonGenerator generator =
				objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), encoding);
		if (prefixJson) {
			generator.writeRaw("{} && ");
		}
		
		SerializationConfig config = objectMapper.getSerializationConfig();
		Object value = filterModel(model, generator, config, request);
		
//		objectMapper.writeValue(generator, value, config);

		DataOutputAdaptor adaptor = this.buildDataOutputAdaptor(value);
		if (adaptor != null) {
			adaptor.output(request,response, value);
		} else {
			objectMapper.writeValue(generator, value, config);
		}
	}
	
	protected Object filterModel(Map<String, Object> model, JsonGenerator generator,
			SerializationConfig config, HttpServletRequest request) {
		Object result = super.filterModel(model);
		
		if (!(result instanceof Map)) {
			return result;
		}
		
		@SuppressWarnings("unchecked")
		Map<String, Object> filteredModel = (Map<String, Object>)result;		
		if (filteredModel.size() == 1) {
			Object singleObj = filteredModel.values().iterator().next();
//			if (isTreeData(singleObj)) {
//				return filterTreeData(config, singleObj, request);
//			}
			return singleObj;
		}
		
		return filteredModel;
	}
	


	private DataOutputAdaptor buildDataOutputAdaptor(Object singleObj) {
		if (this.dataOutputAdaptors == null) {
			return null;
		} else {
			for (DataOutputAdaptor f : this.dataOutputAdaptors) {
				String formatType = f.getType();
				if (singleObj.getClass().getName().equals(formatType))
					return f;
			}
		}
		return null;
	}

//	
//
//	private Object filterTreeData(SerializationConfig config, Object treeData, HttpServletRequest request) {
//		config.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//		return convertToEasyUITreeData((TreeData)treeData, request.getContextPath());
//	}
//
//	private boolean isTreeData(Object obj) {
//		return obj instanceof TreeData;
//	}
//	
//	protected List<EasyUITreeNode> convertToEasyUITreeData(TreeData treeData, String contextPath) {
//		List<Node> treeNodes = treeData.getNodes();
//		List<EasyUITreeNode> easyUITreeNodes = new ArrayList<EasyUITreeNode>();
//		for (Node node : treeNodes) {
//			easyUITreeNodes.add(convertToEasyUITreeNode(node, contextPath));
//		}
//		
//		return easyUITreeNodes;
//	}
//	
//	protected EasyUITreeNode convertToEasyUITreeNode(Node node, String contextPath) {
//		EasyUITreeNode euNode = new EasyUITreeNode();
//		euNode.setId(node.getId());
//		if (node.getText() != null && node.getUrl() != null) {
//			euNode.setText(getHref(node, contextPath));
//		} else {
//			euNode.setText(node.getText());
//		}
//		
//		euNode.setState(node.hasChild() ? "closed" : "open");
//		if (node.getChildren() != null && node.getChildren().size() > 0) {
//			if (euNode.getChildren() == null) {
//				euNode.setChildren(new ArrayList<EasyUITreeNode>());
//			}
//			
//			for (Node child : node.getChildren()) {
//				euNode.getChildren().add(convertToEasyUITreeNode(child, contextPath));
//			}
//		}
//		
//		return euNode;
//	}
//
//	private String getHref(Node node, String contextPath) {
//		StringBuilder builder = new StringBuilder();
//		builder.append("<a hh='").append(contextPath).append('/').
//			append(node.getUrl()).append("'>").append(node.getText()).
//				append("</a>");
//		
//		return builder.toString();
//	}
//	
	@Override
	public void setPrefixJson(boolean prefixJson) {
		this.prefixJson = prefixJson;
	}
	
	@Override
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Override
	public void setEncoding(JsonEncoding encoding) {
		this.encoding = encoding;
	}

	public void setDataOutputAdaptors(List<DataOutputAdaptor> dataOutputAdaptors) {
		this.dataOutputAdaptors = dataOutputAdaptors;
	}
}
