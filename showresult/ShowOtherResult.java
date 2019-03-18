package showresult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ShowOtherResult {
	public static void showResult(String path,String mutation_txt,String count_sum) throws Exception{
		int implanted_100_count = 71;
		int implanted_200_count = 88;
		BufferedReader br = new BufferedReader(new FileReader(mutation_txt));
		ArrayList<MutationUnit> arr = new ArrayList<>();
		String line = null;
		int del_sum = 0;//删除变异个数
		int ins_sum = 0;//插入变异个数
		int correct_del_sum = 0;
		int capture_del_sum = 0;
		
		//int capture_ins_sum = 0;
		while((line = br.readLine()) != null){
			String[] temp = line.split(" ");
			if(temp[0].equals("D")){
				del_sum++;
			}else{
				ins_sum++;
			}
			MutationUnit mu = new MutationUnit(Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
			arr.add(mu);
		}
		br.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(count_sum));
		File file = new File(path);
		if(file.exists()){
			File[] file2 = file.listFiles();
			for(File file3:file2){
				String s = file3.getAbsolutePath();
				if(s.contains("ins_result")){
					int more_100_correct_ins_sum = 0;
					int more_200_correct_ins_sum = 0;
					int only_pos_more_100=0;
					int only_pos_more_200=0;
					BufferedReader br_ins = new BufferedReader(new FileReader(s));
					String line_ins = null;
					//BufferedWriter bw3 = new BufferedWriter(new FileWriter("test.txt"));
					//int ccc = 0;
					while((line_ins = br_ins.readLine()) != null){
						String[] temp1 = line_ins.split(" ");
						int pos1 = Integer.parseInt(temp1[0]);
						//int length1 = temp1[3].length();
						//int length2 = temp1[4].length();
						//int size1 = length2-length1;
						int size1 = Integer.parseInt(temp1[1]);
						/*if(size1>100){
							capture_ins_sum++;
						}*/
						//int size1 = Integer.parseInt(temp1[1]);
						boolean flag = false;
						for(MutationUnit mu1 : arr){
							int pos = mu1.getPos();
							int size = mu1.getSize();
							if((pos1 >= pos-10 && pos1 <= pos+10) &&(size1 >= size-10 && size1 <= size+10) && (size>100 && size<200)){
								more_100_correct_ins_sum++;
								flag = true;
								break;
							}else if((pos1 >= pos-10 && pos1 <= pos+10) &&(size1 >= size-10 && size1 <= size+10) && (size>=200)){
								more_200_correct_ins_sum++;
								flag = true;
								break;
							}else if((pos1 >= pos-10 && pos1<=pos+10) && (size>100 && size<200)){
								only_pos_more_100++;
							}else if((pos1>= pos-10 && pos1 <= pos+10) && (size > 200)){
								only_pos_more_200++;
							}
						}
						
						/*if(!flag){
							System.out.println(pos1+"    "+size1);
						}*/
						//capture_ins_sum++;
					}
					
					bw.write((more_100_correct_ins_sum*1.0)/implanted_100_count+"	"+(more_200_correct_ins_sum*1.0)/implanted_200_count+
							"	"+(more_100_correct_ins_sum+only_pos_more_100)*1.0/implanted_100_count+"	"+(more_200_correct_ins_sum+only_pos_more_200)*1.0/implanted_200_count);
					bw.newLine();
					bw.flush();
					br_ins.close();
				}
			}
		}
		bw.close();
		
		
		
	}
	public static void main(String[] args) throws Exception {
		ShowOtherResult sr = new ShowOtherResult();
		sr.showResult("D://java_workspace//BiShe//count1//cover70//4_snvsniffer_cover70","test_mutation.txt","snvsniffer_cover70_rate.txt");
	}
}
