import java.util.Scanner;

public class Main 
{
	public static void print_msg(String msg)
	{
		System.out.println(msg);
	}
	
	public static void initialize_grid(int[][]grid, int dimension, int initializer)
	{
		//Integer [] x;
		//x = new Integer [dimension];
		for(Integer i = 0; i < dimension; i++)
		{
			for(int j = 0; j < dimension; j++)
			{
				grid[i][j] = initializer;
			}
			//grid.add(x);
		}
	}
	
	public static void print_grid(int[][]grid, int dimension)
	{
		for(int i = 0; i < dimension; i++)
		{
			for(int j = 0; j < dimension; j++)
			{
				System.out.print(grid[i][j]);
				System.out.print(' ');
			}
			System.out.println();
		}
	}
	
	public static void set_hole(int[][]grid, int x_hole, int y_hole, int hole_value)
	{
		grid[y_hole][x_hole] = hole_value;
	}
	
	public static boolean has_hole(int[][]grid, int dimension, int start_x, int start_y, int initializer)
	{
		boolean has_hole = false;
		
		for(int height_index = 0; height_index < dimension; height_index++)
		{
			for(int width_index = 0; width_index < dimension; width_index++)
			{
				if(grid[start_y + height_index][start_x + width_index] != initializer)
				{
					has_hole = true;
					break;
				}
			}
			
			if(has_hole)
			{
				break;
			}
		}
		
		return has_hole;
	}
	
	public static int divide_and_conquer(int[][]grid, int dimension, int start_x, int start_y, int initializer, int value)
	{
		if(dimension == 2)
		{
			for(int height_index = 0; height_index < dimension; ++height_index)
			{
				for(int width_index = 0; width_index < dimension; ++width_index)
				{
					if(grid[start_y + height_index][start_x + width_index] == initializer)
					{
						grid[start_y + height_index][start_x + width_index] = value;
					}
				}
			}
			value++;
		}
		else
		{
			int
			new_dimension = dimension / 2,
			start_x_nw = start_x, start_y_nw = start_y,
			corner_x_nw = start_x_nw + new_dimension - 1, corner_y_nw = start_y_nw + new_dimension - 1,
			start_x_ne = start_x + new_dimension, start_y_ne = start_y,
			corner_x_ne = start_x_ne, corner_y_ne = start_y_ne + new_dimension - 1,
			start_x_sw = start_x, start_y_sw = start_y + new_dimension,
			corner_x_sw = start_x_sw + new_dimension - 1, corner_y_sw = start_y_sw,
			start_x_se = start_x + new_dimension, start_y_se = start_y + new_dimension,
			corner_x_se = start_x_se, corner_y_se = start_y_se;
			
			if(!has_hole(grid, new_dimension, start_x_nw, start_y_nw, initializer))
			{
				grid[corner_y_nw][corner_x_nw] = value;
			}

			if(!has_hole(grid, new_dimension, start_x_ne, start_y_ne, initializer))
			{
				grid[corner_y_ne][corner_x_ne] = value;
			}

			if(!has_hole(grid, new_dimension, start_x_sw, start_y_sw, initializer))
			{
				grid[corner_y_sw][corner_x_sw] = value;
			}

			if(!has_hole(grid, new_dimension, start_x_se, start_y_se, initializer))
			{
				grid[corner_y_se][corner_x_se] = value;
			}
			
			value++;
			

			value = divide_and_conquer(grid, new_dimension, start_x_nw, start_y_nw, initializer, value);
			value = divide_and_conquer(grid, new_dimension, start_x_ne, start_y_ne, initializer, value);
			value = divide_and_conquer(grid, new_dimension, start_x_sw, start_y_sw, initializer, value);
			value = divide_and_conquer(grid, new_dimension, start_x_se, start_y_se, initializer, value);
		}
		
		return value;
	}
	
	public static void main(String [] args)
	{
		int dimension, value , initializer, x_hole, y_hole;
		int [][]grid;
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		while(true)
		{
			value = initializer = -1;
			print_msg("Please enter the grid dimension (has to be an integer, greater that 1, and a power of 2) >");
			dimension = input.nextInt();
			if(dimension <= 1 || (dimension & (dimension - 1)) != 0)
			{
				print_msg("The input is not a valid dimension!");
				continue;
			}
			
			print_msg("Please enter the X coordinate of the hole (0 based index) >");
			x_hole = input.nextInt();
			if(x_hole < 0 || x_hole > dimension - 1)
			{
				print_msg("The input is not a valid coordinate!");
				continue;
			}
			
			print_msg("Please enter the Y coordinate of the hole (0 based index) >");
			y_hole = input.nextInt();
			if(y_hole < 0 || y_hole > dimension - 1)
			{
				print_msg("The input is not a valid coordinate!");
				continue;
			}
			
			grid = new int [dimension][dimension];
		
			initialize_grid(grid, dimension, value++);
			set_hole(grid, x_hole, y_hole, value++);
			value = divide_and_conquer(grid, dimension, 0, 0, initializer, value);
			print_grid(grid, dimension);
		}
	}	
}
