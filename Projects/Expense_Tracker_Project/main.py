import os


print("Greetings, This is an expense tracker application ")
print("We hope you enjoy this small project of mine")
while(True):

    print("""\nPlease enter 1 to add expenses,
enter 2 to view expenses,
enter 3 to view savings,
enter 4 to add savings,
enter 5 to add savings and expenses cost,
enter 6 to exit \n""")
    try:
        choice_entered = int(input())

    except ValueError:
        print("Please Enter a number")
        continue

    valid_choices = {1,2,3,4,5,6}
    if choice_entered not in valid_choices:
        print("Please enter a valid choice of 1,2,3,4")
        continue



    if choice_entered==1:
        print("You have pressed 1 to add expenses")
        print("""Please add expenses in this format
Expense Reason : amount in INR
Example -> Shopping : 500
Anything else will be disregarded""")
        expenses = input()
        if ":" not in expenses:
            print("Colon : was not found in the expenses, please add it and redo it in the right format")
            continue
        expense_reason, cost = expenses.split(":",1)
        expense_reason.strip()
        try:
            cost = int(cost.strip())
        except ValueError:
            print("Enter cost as number")
            continue

        if not expense_reason.strip() or expense_reason.strip().isdigit():
            print("Please enter the expense reason as a non-empty string, not just numbers.")
            continue

        if cost<0:
            print("The cost added is less than 0, which is not possible")
            continue

        with open('expenses.txt','a') as file:
            file.write(f"{expense_reason} : {cost}\n")

        print("Added expenses\n")
        print(expense_reason,":",cost)


    elif choice_entered==2:

        print("You have pressed 2 to view expenses")

        if os.path.getsize('expenses.txt')==0:
            print("No expenses to view")
            continue
        sum = 0

        with open('expenses.txt','r') as file:
            for line in file:
                parts = line.split(":", 1)
                if len(parts) > 1:
                    try:
                        sum += int(parts[1].strip())
                    except ValueError:
                        print(f"Invalid amount in line: {line.strip()}")
            file.seek(0)
            print(file.read())
        print(f"Total expenditure is {abs(sum)}")
        print("Thank you for view expenses")


    elif choice_entered==3:


        print("You have pressed 3 to view savings")
        if os.path.getsize('savings.txt')==0:
            print("No expenses to view")
            continue

        sum = 0
        with open('savings.txt', 'r') as file:
            for line in file:
                parts = line.split(":", 1)
                if len(parts) > 1:
                    try:
                        sum += int(parts[1].strip())
                    except ValueError:
                        print(f"Invalid amount in line: {line.strip()}")
            file.seek(0)
            print(file.read())
        print(f"Total savings is {abs(sum)}")
        print("Thank you for view savings")


    if choice_entered==4:
        print("You have pressed 4 to add savings")
        print("""Please add savings in this format
savings Reason : amount in INR
Example -> Grants : 500
Anything else will be disregarded""")
        savings = input()
        if ":" not in savings:
            print("Colon : was not found in the savings, please add it and redo it in the right format")
            continue
        savings_reason, cost = savings.split(":",1)
        savings_reason.strip()
        try:
            cost = int(cost.strip())
        except ValueError:
            print("Enter cost as number")
            continue

        if not savings_reason.strip() or savings_reason.strip().isdigit():
            print("Please enter the savings reason as a non-empty string, not just numbers.")
            continue
        if cost<0:
            print("The cost added is less than 0, which is not possible")
            continue

        with open('savings.txt','a') as file:
            file.write(f"{savings_reason} : {cost}\n")

        print("Added savings\n")
        print(savings_reason,":",cost)


    elif choice_entered==5:

        print("You have pressed 5 to add expenses and add savings")
        sum = 0
        with open('expenses.txt', 'r') as file:
            for line in file:
                parts = line.split(":", 1)
                if len(parts) > 1:
                    try:
                        sum -= int(parts[1].strip())
                    except ValueError:
                        print(f"Invalid amount in line: {line.strip()}")
        print(f"Total expenditure is {abs(sum)}")

        saving = 0
        with open('savings.txt', 'r') as file:
            for line in file:
                parts = line.split(":", 1)
                if len(parts) > 1:
                    try:
                        saving += int(parts[1].strip())
                    except ValueError:
                        print(f"Invalid amount in line: {line.strip()}")
        print(f"Total savings is {saving}")

        sum+= saving
        print(f"Total sum of expenditure and savings is {sum}")

    elif choice_entered==6:
        print("Thank you for using our app")
        break
