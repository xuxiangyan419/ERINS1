package showresult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ShowVRindelResult {
	public static void showResult(String path,String mutation_txt,String count_sum) throws Exception{
		int implanted_100_count = 26; //大于100小于200的个数
		int implanted_200_count = 30; //大于200的个数
		BufferedReader br = new BufferedReader(new FileReader(mutation_txt));
		ArrayList<MutationUnit> arr = new ArrayList<>();
		String line = null;
		//int del_sum = 0;//删除变异个数
		int ins_sum = 0;//插入变异个数
		//int correct_del_sum = 0;
		//int capture_del_sum = 0;
		
		//int capture_ins_sum = 0;
		while((line = br.readLine()) != null){
			String[] temp = line.split(" ");
			if(temp[0].equals("I")){
				ins_sum++;
				MutationUnit mu = new MutationUnit(Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
				arr.add(mu);
			}
			
		}
		br.close();
		double sensitivity_100_sum = 0;
		double sensitivity_200_sum = 0;
		BufferedWriter bw = new BufferedWriter(new FileWriter(count_sum,true));
		File file = new File(path);
		if(file.exists()){
			File[] file2 = file.listFiles();
			for(File file3:file2){
				File[] file4 = file3.listFiles();
				for(File file5 : file4){
					String s = file5.getAbsolutePath();
					if(s.contains("insertion_result")){
						int more_100_correct_ins_sum = 0;
						int more_200_correct_ins_sum = 0;
						int only_pos_more_100=0;
						int only_pos_more_200=0;
						BufferedReader br_ori_ins = new BufferedReader(new FileReader(s));
						ArrayList<MutationUnit> arr2 = new ArrayList<>();
						String line_ori_ins = null;
						int count = 0;
						while((line_ori_ins = br_ori_ins.readLine()) != null){
							String[] temp1 = line_ori_ins.split("	");
							if(count == 0){
								int pos1 = Integer.parseInt(temp1[1]);
								int size1 = Integer.parseInt(temp1[2]);
								MutationUnit mu = new MutationUnit(pos1, size1);
								arr2.add(mu);
							}else{
								int pos1 = Integer.parseInt(temp1[1]);
								int size1 = Integer.parseInt(temp1[2]);
								MutationUnit last_mu = arr2.get(arr2.size()-1);
								int last_pos = last_mu.getPos();
								if(Math.abs(pos1-last_pos) < 50){
									int last_size = last_mu.getSize();
									last_size = last_size+size1;
									arr2.remove(arr2.size()-1);
									MutationUnit newmu = new MutationUnit(last_pos, last_size);
									arr2.add(newmu);
								}else{
									MutationUnit mu = new MutationUnit(pos1, size1);
									arr2.add(mu);
								}
							}
							count++;
						}
						for(int i = 0;i<arr2.size();i++){
							MutationUnit mu = arr2.get(i);
							int pos1 = mu.getPos();
							
							int size1 = mu.getSize();
							/*if(size1>100){
								capture_ins_sum++;
							}*/
							//int size1 = Integer.parseInt(temp1[1]);
							
							for(MutationUnit mu1 : arr){
								int pos = mu1.getPos();
								int size = mu1.getSize();
								if((pos1 >= pos-10 && pos1 <= pos+10) &&(size1 >= size-10 && size1 <= size+10) && (size>=100 && size<200)){
									more_100_correct_ins_sum++;
									break;
								}else if((pos1 >= pos-10 && pos1 <= pos+10) &&(size1 >= size-10 && size1 <= size+10) && (size>=200)){
									more_200_correct_ins_sum++;
									break;
								}else if((pos1 >= pos-10 && pos1<=pos+10) && (size>100 && size<200)){
									only_pos_more_100++;
								}else if((pos1>= pos-10 && pos1 <= pos+10) && (size > 200)){
									only_pos_more_200++;
								}
							}
						}
						
						sensitivity_100_sum = sensitivity_100_sum+((more_100_correct_ins_sum*1.0)/implanted_100_count);
						sensitivity_200_sum = sensitivity_200_sum+((more_200_correct_ins_sum*1.0)/implanted_200_count);
						br_ori_ins.close();
					}
				}
				
			}
		}
		String output_sensitivity_100_sum = String.format("%.3f", sensitivity_100_sum/50);
		String output_sensitivity_200_sum = String.format("%.3f", sensitivity_200_sum/50);
		bw.write(output_sensitivity_100_sum+" "+output_sensitivity_200_sum);
		bw.newLine();
		bw.flush();
		bw.close();
		
		
		
	}
	public static void main(String[] args) throws Exception {
		ShowVRindelResult sr = new ShowVRindelResult();
		sr.showResult("G://cover20","all_mutations.txt","VRindel_100_200_sensitivity.txt");
		sr.showResult("G://cover30","all_mutations.txt","VRindel_100_200_sensitivity.txt");
		sr.showResult("G://cover40","all_mutations.txt","VRindel_100_200_sensitivity.txt");
		sr.showResult("G://cover50","all_mutations.txt","VRindel_100_200_sensitivity.txt");
		sr.showResult("G://cover60","all_mutations.txt","VRindel_100_200_sensitivity.txt");
		sr.showResult("G://cover70","all_mutations.txt","VRindel_100_200_sensitivity.txt");
	}
}
