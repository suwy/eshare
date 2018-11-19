package com.yunde.website.allpay.common.util;

import com.jfinal.ext.kit.ModelKit;
import com.jfinal.ext.kit.RecordKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import java.util.*;

public class NodeKit {
	public static List<Object> buildNodes(List<? extends Object> list){
		List<Node> nodes = new ArrayList<Node>();
		init(nodes, list);
		
		List<Object> newList = new ArrayList<Object>();
		buildModel(list, build(nodes), newList);
		return newList;
	}
	
	@SuppressWarnings("rawtypes")
	public static void init(List<Node> nodes, List<? extends Object> list){
		for(Object object : list){
			if(object instanceof Model){
				Model model = (Model)object;
				nodes.add(new Node(model.getStr("id"), model.getStr("name"), model.getStr("parentId"), model.getInt("orderNo")));
			}
			else if(object instanceof Record){
				Record record = (Record)object;
				nodes.add(new Node(record.getStr("id"), record.getStr("name"), record.getStr("parentId"), record.getInt("orderNo")));
			}
			else{
				continue;
			}
		}
	}
	
	private static void buildModel(List<? extends Object> list, List<Node> nodes, List<Object> nodeList){
		for (Node node : nodes) {
			Map<String, Object> map = getDataById(list, node.getId());
			List<Node> childrens = node.getChildrens();
			if(childrens != null && childrens.size() > 0){
				List<Object> newList = new ArrayList<Object>();
				buildModel(list, childrens, newList);
				map.put("childrens", newList);
			}
			nodeList.add(map);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static Map<String, Object> getDataById(List<? extends Object> list, String id){
		for(Object object : list){
			if(object instanceof Model){
				Model model = (Model)object;
				if(model.getStr("id").equals(id)){
					return ModelKit.toMap(model);
				}
			}
			else if(object instanceof Record){
				Record record = (Record)object;
				if(record.getStr("id").equals(id)){
					return RecordKit.toMap(record);
				}
			}
			else{
				continue;
			}
		}
		return null;
	}
	
	private static List<Node> build(List<Node> nodes){
		List<Node> rootNodes = new ArrayList<Node>();
		for (Node node : nodes) {
			if (isRoot(node.getParentId())) {
				rootNodes.add(node);
			}
			for (Node t : nodes) {
				if (!StrKit.isBlank(t.getParentId()) && t.getParentId().equals(node.getId())) {
					if (node.getChildrens() == null) {
						List<Node> childrens = new ArrayList<Node>();
						childrens.add(t);
						node.setChildrens(childrens);
					} else {
						List<Node> childrens = node.getChildrens();
						childrens.add(t);
						sort(childrens);
						node.setChildrens(childrens);
					}
				}
			}
		}
		sort(rootNodes);
		return rootNodes;
	}
	
	private static boolean isRoot(String parentId){
		return StrKit.isBlank(parentId) || "0".equals(parentId);
	}
	
	private static void sort(List<Node> list){
		Collections.sort(list, new Comparator<Node>() {
            public int compare(Node arg0, Node arg1) {
                return arg0.getOrder().compareTo(arg1.getOrder());
            }
        });
	}
	
	public static void main(String[] args) {
		Node tree1 = new Node("1", "顶层节点1", "0", 1);
		Node tree2 = new Node("2", "顶层节点2", "0", 1);
		Node tree3 = new Node("3", "顶层节点3", "0", 1);

		Node tree4 = new Node("4", "二级节点4", "1", 1);
		Node tree5 = new Node("5", "二级节点5", "2", 1);
		Node tree6 = new Node("6", "二级节点6", "3", 1);

		Node tree7 = new Node("7", "三级节点7", "4", 1);
		Node tree8 = new Node("8", "三级节点8", "4", 1);
		Node tree9 = new Node("9", "三级节点9", "5", 1);
		Node tree10 = new Node("10", "三级节点9", "9", 1);
		Node tree11 = new Node("11", "三级节点9", "9", 1);
		Node tree12 = new Node("12", "三级节点9", "9", 1);

		List<Node> nodes = new ArrayList<Node>();
		nodes.add(tree12);
		nodes.add(tree11);
		nodes.add(tree10);
		nodes.add(tree9);
		nodes.add(tree8);
		nodes.add(tree7);
		nodes.add(tree6);
		nodes.add(tree5);
		nodes.add(tree4);
		nodes.add(tree3);
		nodes.add(tree2);
		nodes.add(tree1);

		for (Node tree : build(nodes)) {
			System.out.println(tree.toString());
		}

	}
}

class Node{
	private String id;
	private String name;
	private String parentId;
	private Integer order;
	private List<Node> childrens;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public List<Node> getChildrens() {
		return childrens;
	}

	public Node(String id, String name, String parentId, int order) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.order = order;
	}

	public void setChildrens(List<Node> childrens) {
		this.childrens = childrens;
	}

	@Override
	public String toString() {
		return "Tree [id=" + id + ", name=" + name + ", parentId=" + parentId
				+ ", childrens=" + childrens + "]";
	}
}